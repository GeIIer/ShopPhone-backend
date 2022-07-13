package com.example.demo.authorization.api.controllers;

import com.example.demo.authorization.api.dto.AccountDTO;
import com.example.demo.authorization.api.service.UserService;
import com.example.demo.authorization.entities.AccountEntity;
import com.example.demo.authorization.entities.RoleEntity;
import com.example.demo.authorization.repositories.AccountInterface;
import com.example.demo.authorization.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/services/controller/user")
@AllArgsConstructor
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/getUser")
    public AccountDTO loadUserByEmail(@RequestParam(value = "email", required = false) String email) {
        if (email != null){
            AccountEntity accountEntity = accountRepository.findByEmail(email);
            return new AccountDTO(accountEntity.getId(),
                    accountEntity.getFirstName(),
                    accountEntity.getLastName(),
                    accountEntity.getEmail(),
                    accountEntity.getPhoneNumber());
        }
        else return null;
    }


    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody AccountEntity account) {
        return new ResponseEntity<>(userService.saveDto(account), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AccountEntity account) {
        if (accountRepository.existsAccountEntityByEmail(account.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email уже существует");
        }

        long id = accountRepository.count() + 1;
        AccountEntity accountEntity = new AccountEntity(
                id,
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getPhoneNumber(),
                bCryptPasswordEncoder.encode(account.getPassword()),
                new ArrayList<>());
        accountRepository.save(accountEntity);
        return ResponseEntity.ok("Регистрация прошла успешно");
    }
}
