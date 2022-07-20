package com.example.demo.authorization.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.authorization.entities.AccountEntity;
import com.example.demo.authorization.repositories.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.demo.authorization.api.service.SecurityConstants.EXPIRATION_TIME;
import static com.example.demo.authorization.api.service.SecurityConstants.SECRET;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final AccountRepository accountRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AccountRepository accountRepository) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        setFilterProcessesUrl("/api/services/controller/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AccountEntity creds = new ObjectMapper()
                    .readValue(req.getInputStream(), AccountEntity.class);


            AccountEntity account = accountRepository.findByEmail(creds.getEmail());

            if (account == null) {
                throw new BadCredentialsException("Не существует: "+account);
            }

            String[] roles = new String[account.getRoles().size()];
            for (int i = 0; i < roles.length; i++){
                roles[i] = account.getRoles().get(i).getName();
            }

            UserDetails principal = User.builder()
                    .username(account.getEmail())
                    .password(account.getPassword())
                    .roles(roles)
                    .build();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    principal, creds.getPassword(), principal.getAuthorities()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));

        String body = "{ \"email\": \"" + ((User) auth.getPrincipal()).getUsername() + "\", \"token\": \"" + token + "\"}";

        res.getWriter().write(body);
        res.getWriter().flush();
    }
}
