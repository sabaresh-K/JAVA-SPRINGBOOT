package com.example.productjpa.service;

import com.example.productjpa.model.Products;
import com.example.productjpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class productsService {

    @Autowired
    private ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Products> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Products createProduct(Products product) {
        return productRepository.save(product);
    }

    public Products updateProduct(Long id, Products productDetails) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setQuantity(productDetails.getQuantity());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}