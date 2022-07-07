package com.example.demo.api.controllers;

import com.example.demo.api.factory.ProductDTOFactory;
import com.example.demo.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductDTOFactory productDTOFactory;

    @GetMapping("/products")
    public List getProducts() {
        return (List) productRepository.findAll();
    }
}
