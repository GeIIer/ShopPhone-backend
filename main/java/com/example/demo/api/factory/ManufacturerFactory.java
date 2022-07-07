package com.example.demo.api.factory;

import com.example.demo.api.dto.ManufacturerDTO;
import com.example.demo.store.entities.ManufacturerEntity;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerFactory {

    public ManufacturerDTO makeManufacturerDTO (ManufacturerEntity entity){

        return ManufacturerDTO.builder()
                .idManuFact(entity.getIdManuFact())
                .nameManuFact(entity.getNameManuFact())
                .build();
    }
}
