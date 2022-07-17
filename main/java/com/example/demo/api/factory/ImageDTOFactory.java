package com.example.demo.api.factory;

import com.example.demo.api.dto.ImageDTO;
import com.example.demo.api.dto.ManufacturerDTO;
import com.example.demo.store.entities.ImageEntity;
import com.example.demo.store.entities.ManufacturerEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ImageDTOFactory {

    public ImageDTO makeImageDTO (ImageEntity entity){

        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/images/")
                .path(entity.getId())
                .toUriString();

        return  ImageDTO.builder()
                .id(entity.getId())
                .contentType(entity.getContentType())
                .name(entity.getName())
                .size(entity.getSize())
                .url(downloadURL)
                .build();
    }
}
