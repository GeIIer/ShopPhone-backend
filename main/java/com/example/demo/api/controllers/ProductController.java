package com.example.demo.api.controllers;

import com.example.demo.api.dto.ProductDTO;
import com.example.demo.api.factory.ProductDTOFactory;
import com.example.demo.store.entities.ProductEntity;
import com.example.demo.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping({"/api/products"})
public class ProductController {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductDTOFactory productDTOFactory;

    private static final String GET_PRODUCT = "/product";

    @GetMapping()
    public List<ProductDTO> getProducts(@RequestParam(value = "category_id", required = false) Long optionalCategoryId) {

        if (optionalCategoryId != null){
          return (productRepository.findAllByCategory_IdCategory(optionalCategoryId)
                  .stream().map(productDTOFactory::makeProductDTO)
                  .collect(Collectors.toList()));
        }
        else {
            return (productRepository
                    .findAll())
                    .stream()
                    .map(productDTOFactory::makeProductDTO)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping(GET_PRODUCT)
    public ProductDTO getProduct (@RequestParam(value = "product", required = false) Long idProduct) {
        ProductEntity entity = productRepository.findByIdProduct(idProduct);
        return productDTOFactory.makeProductDTOWithImage(entity);
    }
}
