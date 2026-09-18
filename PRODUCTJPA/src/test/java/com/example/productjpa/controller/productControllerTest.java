package com.example.productjpa.controller;

import com.example.productjpa.model.products;
import com.example.productjpa.service.productsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class productControllerTest {

    @Mock
    private productsService service;

    @InjectMocks
    private productController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDisplayAllProducts() {
        products p1 = new products(1, "Laptop", 999.99, 10, "Electronics");
        products p2 = new products(2, "Phone", 499.99, 20, "Electronics");
        when(service.getAllProducts()).thenReturn(Arrays.asList(p1, p2));

        List<products> result = controller.displayAllProducts();
        assertEquals(2, result.size());
    }

    @Test
    void testGetProductById_Found() {
        products p = new products(1, "Laptop", 999.99, 10, "Electronics");
        when(service.getProductById(1)).thenReturn(Optional.of(p));

        ResponseEntity<products> response = controller.getProductById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Laptop", response.getBody().getProductName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(service.getProductById(1)).thenReturn(Optional.empty());

        ResponseEntity<products> response = controller.getProductById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAddProduct() {
        products p = new products(1, "Laptop", 999.99, 10, "Electronics");
        when(service.addProduct(any(products.class))).thenReturn(p);

        ResponseEntity<products> response = controller.addProduct(p);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Laptop", response.getBody().getProductName());
    }

    @Test
    void testUpdateProduct() {
        products p = new products(1, "Laptop", 999.99, 10, "Electronics");
        when(service.updateProduct(eq(1), any(products.class))).thenReturn(p);

        ResponseEntity<products> response = controller.updateProduct(1, p);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Laptop", response.getBody().getProductName());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(service).deleteProduct(1);

        ResponseEntity<Void> response = controller.deleteProduct(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteProduct(1);
    }
}
