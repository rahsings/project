package com.fastfashion.web.dto;

public class AuthDtos {
    public static class RegisterRequest {
        public String email;
        public String password;
        public String name;
        public String phone;
    }
    public static class LoginRequest {
        public String email;
        public String password;
    }
    public static class TokenResponse {
        public String token;
        public TokenResponse(String token){this.token=token;}
    }
}
