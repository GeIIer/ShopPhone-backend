package com.example.demo.api.factory;

import com.example.demo.api.dto.ImageDTO;
import com.example.demo.api.dto.ProductDTO;
import com.example.demo.store.entities.ImageEntity;
import com.example.demo.store.entities.ProductEntity;
import com.example.demo.store.repositories.CategoryRepository;
import com.example.demo.store.repositories.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductDTOFactory {

    @Autowired
    private final ImageDTOFactory imageDtoFactory;

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

    public ProductDTO makeProductDTOWithImage(ProductEntity entity){
        List<ImageDTO> images = entity.getImageEntityList()
                .stream()
                .map(imageDtoFactory::makeImageDTO).toList();

        return ProductDTO.builder()
                .idProduct(entity.getIdProduct())
                .nameProduct(entity.getNameProduct())
                .categoryDTO(entity.getCategory().getNameCategory())
                .manufacturerDTO(entity.getManufacturer().getNameManuFact())
                .description(entity.getDescription())
                .characteristic(entity.getCharacteristic())
                .urlImage(images
                        .stream()
                        .map(ImageDTO::getUrl)
                        .collect(Collectors.toList()))
                .price(entity.getPrice())
                .build();
    }
}
