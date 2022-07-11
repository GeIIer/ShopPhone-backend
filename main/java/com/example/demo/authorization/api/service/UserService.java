package com.example.demo.authorization.api.service;

import com.example.demo.authorization.entities.AccountEntity;
import com.example.demo.authorization.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountEntity userEntity = accountRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
    }

    @Transactional(rollbackFor = Exception.class)
    public String saveDto(AccountEntity accountDTO) {
        accountDTO.setPassword(bCryptPasswordEncoder.encode(accountDTO.getPassword()));
        return new User(accountDTO.getEmail(), accountDTO.getPassword(), new ArrayList<>()).getUsername();
    }
}
