package com.nagarro.exittest.impl;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.exittest.models.Product;
import com.nagarro.exittest.models.Status;
import com.nagarro.exittest.repository.ProductRepository;
import com.nagarro.exittest.services.ProductService;

import javassist.NotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	// Fetch products by name, brand, or product code
	public List<Product> fetchProductByProductNameOrBrandOrProductCode(String query) {
		if (query != null) {
			return productRepository.findByKeyword(query);
		}
		return productRepository.findAll();
	}

	// Save a product
	public Product saveProduct(Product product) {
		System.out.println(product.toString());
		return this.productRepository.save(product);
	}

	// Show a single product by ID
	public Product showSingleProduct(Long productId) {
		return this.productRepository.findById(productId).orElse(null);
	}
	

	// Find a product by product code
	public Product findByProductCode(String productCode) {
		return this.productRepository.findByProductCode(productCode);
	}

	// Find all products
	public List<Product> findAll() {
		return this.productRepository.findAll();
	}

	// Add a product
	public Status addProduct(@Valid Product product) {
		List<Product> products = productRepository.findAll();
		for (Product prod : products) {
			if (prod.equals(product)) {
				System.out.println("Product Already exists!");
				return Status.PRODUCT_ALREADY_EXISTS;
			}
		 }
		productRepository.save(product);
		return Status.SUCCESS;
	}
	
	// Get the number of products
	public int getNumberofProducts() {
		return (int) this.productRepository.count();
	}

	// Delete a product by ID
	public void deleteProduct(Long productId) throws NotFoundException {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
		productRepository.delete(product);
	}

}
