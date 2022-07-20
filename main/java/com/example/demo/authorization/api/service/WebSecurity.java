package com.example.demo.authorization.api.service;

import com.example.demo.authorization.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {
    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountRepository accountRepository;
    @Autowired
    public WebSecurity(@Qualifier("userService") UserService userDetailsService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       AccountRepository accountRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {


        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http
                .cors(Customizer.withDefaults())
                .csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.IMAGE_PRODUCT_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.PRODUCTS_URL).permitAll()
                .antMatchers(SecurityConstants.ORDERS_URL).hasRole("USER")
                .antMatchers(SecurityConstants.GET_ORDERS_URL).hasRole("USER")
                .antMatchers(SecurityConstants.GET_PROFILE_URL).hasRole("USER")
                .anyRequest().hasRole("ADMIN").and()//убрать общий доступ на запросы

                .addFilter(new JWTAuthenticationFilter(authenticationManager, accountRepository))

                .addFilter(new JWTAuthorizationFilter(authenticationManager, accountRepository))
                .authenticationManager(authenticationManager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
