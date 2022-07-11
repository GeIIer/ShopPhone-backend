package com.example.demo.authorization.api.controllers;

import com.example.demo.authorization.api.service.UserService;
import com.example.demo.authorization.entities.AccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/services/controller/user")
@AllArgsConstructor
public class MainController {

    private UserService userService;

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody AccountEntity accountDTO) {
        return new ResponseEntity<>(userService.saveDto(accountDTO), HttpStatus.OK);
    }

}
