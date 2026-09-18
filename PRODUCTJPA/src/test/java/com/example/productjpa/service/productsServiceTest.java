package com.example.productjpa.service;

import com.example.productjpa.model.products;
import com.example.productjpa.repository.productsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class productsServiceTest {

    @Mock
    private productsRepository repository;

    @InjectMocks
    private productsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        products p1 = new products(1, "Laptop", 999.99, 10, "Electronics");
        products p2 = new products(2, "Phone", 499.99, 20, "Electronics");
        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<products> result = service.getAllProducts();
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getProductName());
    }

    @Test
    void testGetProductById() {
        products p = new products(1, "Laptop", 999.99, 10, "Electronics");
        when(repository.findById(1)).thenReturn(Optional.of(p));

        Optional<products> result = service.getProductById(1);
        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getProductName());
    }

    @Test
    void testAddProduct() {
        products p = new products(1, "Laptop", 999.99, 10, "Electronics");
        when(repository.save(p)).thenReturn(p);

        products result = service.addProduct(p);
        assertNotNull(result);
        assertEquals("Laptop", result.getProductName());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(repository).deleteById(1);
        service.deleteProduct(1);
        verify(repository, times(1)).deleteById(1);
    }
}
