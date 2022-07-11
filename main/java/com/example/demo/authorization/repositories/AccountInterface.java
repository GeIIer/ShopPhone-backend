package com.example.demo.authorization.repositories;

import com.example.demo.authorization.entities.AccountEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountInterface extends UserDetailsService {
    AccountEntity findByEmail(String email);

    AccountEntity save(AccountEntity registration);
}

