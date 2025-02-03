package com.spring.shoppingCart.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.spring.shoppingCart.dto.ImageDto;
import com.spring.shoppingCart.dto.ProductDto;
import com.spring.shoppingCart.exceptions.AlreadyExistsException;
import com.spring.shoppingCart.exceptions.ProductNotFoundException;
import com.spring.shoppingCart.exceptions.RecourceNotFoundException;
import com.spring.shoppingCart.model.Category;
import com.spring.shoppingCart.model.Image;
import com.spring.shoppingCart.model.Product;
import com.spring.shoppingCart.repository.CategoryRepository;
import com.spring.shoppingCart.repository.ImageRepository;
import com.spring.shoppingCart.repository.ProductRepository;
import com.spring.shoppingCart.request.AddProductRequest;
import com.spring.shoppingCart.request.UpdateProductRequest;


@Service
public class ProductServiceImplementation implements ProductService{

	
	private final ProductRepository  productRepository;
	private final CategoryRepository categoryRepository;
	private final ImageRepository imageRepository;
	
	private final ModelMapper modelMapper;
	
	public ProductServiceImplementation(ProductRepository productRepository, CategoryRepository categoryRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
		super();
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.imageRepository = imageRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Product addProduct(AddProductRequest request) {
	
		//check category is found in DB or not
		//if yes , save new product in that category
		// if No , create new category
		// save new product in this category
		
		if(isProductExists(request.getName() , request.getBrand())) {
			throw new AlreadyExistsException(request.getBrand() + "  " + request.getName() + " already exists , you may want to update this product instead !!");
		}
		
		Category category= Optional.ofNullable(
				 
				categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet( () -> {
						Category newCategory =new Category(request.getCategory().getName());
						return categoryRepository.save(newCategory);
				});
		request.setCategory(category);
		
		return productRepository.save(createProduct(request, category));
	}

	
	private boolean isProductExists(String name, String brand) {
		
		return productRepository.existsByNameAndBrand(name,brand);
	}
	
	
	private Product createProduct(AddProductRequest request , Category category) {
		
		return new Product(
				
				request.getName() ,
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category
				);
	}
	
	@Override
	public Product getProductById(long id) {
		
		return productRepository.findById(id)
				     .orElseThrow(() -> new ProductNotFoundException("Product Not Found ..."));
	}

	@Override
	public void deleteProduct(long id) {
		
		productRepository.findById(id).ifPresentOrElse(
				                                              productRepository::delete
				                                             , () -> {throw new ProductNotFoundException("Product Not Found ..."); }
				                                             );
		
	}
	
	/***************************** Update ***************************************/

	@Override
	public Product updateProduct(UpdateProductRequest request , long productId) {
		
		return productRepository.findById(productId)
				    .map(existingproduct -> updateExistingProduct(existingproduct, request))
				    .map(productRepository :: save)
				    .orElseThrow(() -> new RecourceNotFoundException("Product Not Found !!"));
		
	}
	
	private Product updateExistingProduct(Product exisitingProduct , UpdateProductRequest request) {
		
		exisitingProduct.setName(request.getName());
		exisitingProduct.setDescription(request.getDescription());
		exisitingProduct.setBrand(request.getBrand());
		exisitingProduct.setInventory(request.getInventory());
		exisitingProduct.setPrice(request.getPrice());
		
		Category category = categoryRepository.findByName(request.getCategory().getName());
		
		exisitingProduct.setCategory(category);
		return exisitingProduct;
		
		
	}

	
	/***************************** get All Products ***************************************/
	@Override
	public List<Product> getAllProducts() {
		
		return productRepository.findAll();

	}

	@Override
	public List<Product> getProductsByCategory(String categoryName) {
		
		return productRepository.findByCategoryName(categoryName);
	}

	@Override
	public List<Product> getProductsByBrand(String brandName) {
		
		return productRepository.findByBrand(brandName);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String categoryName ,String brandName) {
		
		return productRepository.findByCategoryNameAndBrand(categoryName , brandName);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brandName, String name) {
		
		return productRepository.findByBrandAndName(brandName , name);
	}

	@Override
	public Long countProductsByBrandAndName(String brandName, String name) {
		return productRepository.countByBrandAndName(brandName,name);
	}
	
	@Override
	public List<ProductDto> getConvertedDtos(List<Product> products){
		
		return products.stream().map(this::convertToDto).toList();
	}

	@Override
	public ProductDto convertToDto(Product product) {
		
		ProductDto productDto =  modelMapper.map(product, ProductDto.class);
		
		List<ImageDto> imageDtos= new ArrayList<>();
		List<Image> images = imageRepository.findByProductId(product.getId());
		
		for(Image m : images) {
	
			ImageDto imageDto = new ImageDto();
			
			imageDto.setImageId(m.getId());
			imageDto.setFileName(m.getFileName());
			imageDto.setFileType(m.getFileType());
			imageDto.setDownloadUrl(m.getDownlaodUrl());
			
			imageDtos.add(imageDto);
		}
		
		//this line instead of For loop but doesn't work with me
		//List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
		
		productDto.setImages(imageDtos);
		
		
		return productDto;
		
	}
	

}
