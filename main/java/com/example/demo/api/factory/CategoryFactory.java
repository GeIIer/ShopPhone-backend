package com.example.demo.api.factory;

import com.example.demo.api.dto.CategoryDTO;
import com.example.demo.store.entities.СategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {
    public CategoryDTO makeCategoryDTO(СategoryEntity entity){

        return CategoryDTO.builder()
                .idCategory(entity.getIdCategory())
                .nameCategory(entity.getNameCategory())
                .build();
    }
}
