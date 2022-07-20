package com.example.demo.authorization.api.service;

public class SecurityConstants {
    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 3600_000_000L; // 1000 часов- 3600_000_000L - 900_000 15 минут
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/services/controller/user";
    public static final String LOGIN_URL = "/api/services/controller/user/login";

    public static final String REGISTER_URL = "/api/services/controller/user/register";

    public static final String PRODUCTS_URL = "/api/products/**";

    public static final String IMAGE_PRODUCT_URL = "/api/images/**";

    public static final String ORDERS_URL = "api/order/**";
    public static final String GET_ORDERS_URL = "api/orders";

    public static final String GET_PROFILE_URL = "/api/services/controller/user/getUser";


}
