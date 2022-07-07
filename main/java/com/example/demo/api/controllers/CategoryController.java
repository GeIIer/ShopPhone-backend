package com.example.demo.api.controllers;

import com.example.demo.store.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    private final CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List getCategories() {
        return (List) categoryRepository.findAll();
    }
}
