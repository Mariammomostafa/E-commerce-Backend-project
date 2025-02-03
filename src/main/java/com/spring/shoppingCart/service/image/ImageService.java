package com.spring.shoppingCart.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.shoppingCart.dto.ImageDto;
import com.spring.shoppingCart.model.Image;

public interface ImageService {
	
	Image getImageById(long id);
	
	void deleteImage(long id);
	
	List<ImageDto> saveImages(List<MultipartFile> files , long productId);
	 
	Image updateImage(MultipartFile file , long imageId);

}
