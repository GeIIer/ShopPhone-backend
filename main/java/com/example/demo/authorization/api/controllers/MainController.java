package com.example.demo.authorization.api.controllers;

import com.example.demo.authorization.api.dto.AccountDTO;
import com.example.demo.authorization.api.service.UserService;
import com.example.demo.authorization.entities.AccountEntity;
import com.example.demo.authorization.entities.RoleEntity;
import com.example.demo.authorization.repositories.AccountRepository;
import com.example.demo.authorization.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private RoleRepository roleRepository;
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
                    accountEntity.getPhoneNumber(),
                    accountEntity.getRoles()
                            .stream()
                            .map(RoleEntity::getName)
                            .collect(Collectors.toList()));
        }
        else return null;
    }


    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody AccountEntity account) {
        return new ResponseEntity<>(userService.saveDto(account), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AccountEntity account) {
        if (account == null) {
            return ResponseEntity.badRequest().body("Error: нет данных");
        }
        if (accountRepository.existsAccountEntityByEmail(account.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email уже существует");
        }
        ArrayList<RoleEntity> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER"));
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setFirstName(account.getFirstName());
        accountEntity.setLastName(account.getLastName());
        accountEntity.setEmail(account.getEmail());
        accountEntity.setPhoneNumber(account.getPhoneNumber());
        accountEntity.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountEntity.setRoles(roles);
        accountRepository.save(accountEntity);
        return ResponseEntity.ok("Регистрация прошла успешно");
    }
}
