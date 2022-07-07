package com.example.demo.api.controllers;

import com.example.demo.api.dto.ProductDTO;
import com.example.demo.api.factory.ProductDTOFactory;
import com.example.demo.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductDTOFactory productDTOFactory;

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        return (productRepository
                .findAll())
                .stream()
                .map(productDTOFactory::makeProductDTO)
                .collect(Collectors.toList());
    }
}
