package com.spring.shoppingCart.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.shoppingCart.dto.ImageDto;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Image;
import com.spring.shoppingCart.model.Product;
import com.spring.shoppingCart.repository.ImageRepository;
import com.spring.shoppingCart.repository.ProductRepository;

@Service
public class ImageServiceImplementation  implements ImageService{

	private final ImageRepository imageRepository;
	
	private final ProductRepository productRepository;
	
	public ImageServiceImplementation(ImageRepository imageRepository, ProductRepository productRepository) {
		super();
		this.imageRepository = imageRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Image getImageById(long id) {
		
		return imageRepository.findById(id)
				    .orElseThrow(() -> new RecourceNotFoundException("Image Not found with id : " +id));
	}

	@Override
	public void deleteImage(long id) {
		
		imageRepository.findById(id).ifPresentOrElse( imageRepository :: delete
				                          , () -> {
				                        	  throw new RecourceNotFoundException("Image Not found with id : " +id);
				                        });
		
	}

	@Override
	public List<ImageDto> saveImages(List<MultipartFile> files, long productId) {
		
		Product product = productRepository.findById(productId).get();
		
		List<ImageDto> savedImageDtos = new ArrayList<>();
		
		for (MultipartFile file : files) {
			
			try {
				Image image = new Image();
				
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);
				
				String buildDownloadUrl = "/api/v1/images/image/download/";
				
				String downloadedUrl = buildDownloadUrl + image.getId();
				image.setDownlaodUrl(downloadedUrl);
				 Image savedImage=  imageRepository.save(image);
				
				savedImage.setDownlaodUrl(buildDownloadUrl + savedImage.getId());
				imageRepository.save(savedImage);
				
				ImageDto imageDto = new ImageDto();
				
				imageDto.setImageId(savedImage.getId());
				imageDto.setFileName(savedImage.getFileName());
				imageDto.setFileType(savedImage.getFileType());
				imageDto.setDownloadUrl(savedImage.getDownlaodUrl());
				
				savedImageDtos.add(imageDto);
			
			} catch (IOException | SQLException e) {
			
				throw new RuntimeException(e.getMessage());
			}		
		}
		
		return savedImageDtos;
	}

	@Override
	public Image updateImage(MultipartFile file, long imageId) {
		
		Image image = getImageById(imageId);
		
		
		try {
			
			image.setFileName(file.getOriginalFilename());
			image.setFileType(file.getContentType());
			image.setImage( new SerialBlob(file.getBytes()));
			imageRepository.save(image);
			
		} catch (SQLException | IOException e) {
			
			throw new RuntimeException(e.getMessage());
			
		} 
		
		return null;
	}

}
