package com.example.productjpa.controller;

import com.example.productjpa.model.Products;
import com.example.productjpa.service.productsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class productController {

    @Autowired
    private productsService productsService;


    @GetMapping
    public List<Products> getAllProducts() {
        return productsService.getAllProducts();
    }

    // GET product by id
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        return productsService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products product) {
        Products savedProduct = productsService.createProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody Products productDetails) {
        try {
            Products updatedProduct = productsService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productsService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}