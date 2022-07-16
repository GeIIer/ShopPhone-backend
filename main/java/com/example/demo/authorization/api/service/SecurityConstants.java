package com.example.demo.authorization.api.service;

public class SecurityConstants {
    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 3600_000_000L; // 1000 часов 900_000 15 минут
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/services/controller/user";
    public static final String LOGIN_URL = "/api/services/controller/user/login";

    public static final String REGISTER_URL = "/api/services/controller/user/register";

    public static final String PRODUCT_URL = "/api/products";
}
