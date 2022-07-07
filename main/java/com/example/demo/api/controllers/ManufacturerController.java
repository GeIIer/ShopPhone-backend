package com.example.demo.api.controllers;

import com.example.demo.store.repositories.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ManufacturerController {

    private final ManufacturerRepository manufacturerRepository;

    @GetMapping("/manufacturers")
    public List getManufacturers() {
        return (List) manufacturerRepository.findAll();
    }
}
