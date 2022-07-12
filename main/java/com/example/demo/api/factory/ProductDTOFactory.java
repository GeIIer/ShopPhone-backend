package com.example.demo.api.factory;

import com.example.demo.api.dto.ProductDTO;
import com.example.demo.store.entities.ProductEntity;
import com.example.demo.store.repositories.CategoryRepository;
import com.example.demo.store.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOFactory {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;
    public ProductDTO makeProductDTO(ProductEntity entity){

        return ProductDTO.builder()
                .idProduct(entity.getIdProduct())
                .nameProduct(entity.getNameProduct())
                .categoryDTO(entity.getCategory().getNameCategory())
                .manufacturerDTO(entity.getManufacturer().getNameManuFact())
                .description(entity.getDescription())
                .characteristic(entity.getCharacteristic())
                .price(entity.getPrice())
                .build();
    }
}
