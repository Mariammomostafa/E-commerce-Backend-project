package com.spring.shoppingCart.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.shoppingCart.dto.ImageDto;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Image;
import com.spring.shoppingCart.response.ApiResponse;
import com.spring.shoppingCart.service.image.ImageService;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

	private final ImageService imageService;
	
	public ImageController(ImageService imageService) {
		super();
		this.imageService = imageService;
	}

	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files ,@RequestParam long productId){
		
		try {
			List<ImageDto> imageDtos= imageService.saveImages(files, productId);
			return ResponseEntity.ok(new ApiResponse("Uploade Success !!" , imageDtos));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					            .body(new ApiResponse("Uploade Failed !!" , e.getMessage()));
		}
		
	} 
    
    
    @GetMapping("/image/download/{imageId}")
  	public ResponseEntity<Resource> downloadImage(@PathVariable long imageId) throws SQLException{
    	
    	Image image = imageService.getImageById(imageId);
    	
    	ByteArrayResource resouce = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
    	return ResponseEntity.ok()
    			.contentType(MediaType.parseMediaType(image.getFileType()))
    			.header(HttpHeaders.CONTENT_DISPOSITION , "attachment ; filename "+ image.getFileName() +" \"  ")
                .body(resouce);
    					
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable long imageId, @RequestBody MultipartFile file){
    	
    	try {
			Image image = imageService.getImageById(imageId);
			
			if(image != null) {
				imageService.updateImage(file, imageId);
				return ResponseEntity.ok(new ApiResponse("Updated  Success !!" , null));
			}
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					                  .body(new ApiResponse(e.getMessage(), null));
		}
    	
    	
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    			              .body(new ApiResponse("Updated  Failed !!" , null));
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable long imageId){
    	
    	try {
			Image image = imageService.getImageById(imageId);
			
			if(image != null) {
				imageService.deleteImage(imageId);
				return ResponseEntity.ok(new ApiResponse("Delete  Success !!" , null));
			}
		} catch (RecourceNotFoundException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					                  .body(new ApiResponse(e.getMessage(), null));
		}
    	
    	
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    			              .body(new ApiResponse("Delete  Failed !!" , null));
    }
    
    
}


