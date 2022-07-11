package com.example.demo.authorization.api.controllers;

import com.example.demo.authorization.entities.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping({"/api"})
public class MainController {

    @PostMapping(value = "/register-user")
    public String regiter(@RequestBody AccountEntity myUser) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return "User created :)";
    }

    @GetMapping(value = "/admin")
    public String admin() {
        return "<h3>Welcome Admin :)</h3>";
    }

    @GetMapping(value = "/user")
    public String user() {
        return "<h3>Hello User :)</h3>";
    }

    @GetMapping(value = "/")
    public String welcome() {
        return "<h3>Welcome :)</h3>";
    }

}
