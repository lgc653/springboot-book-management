package com.example.bookmanagement.jwt;

import lombok.Data;

@Data
public class TokenInfo {
    private String token;
    private Integer status;
    private String message;

    public TokenInfo(String token, Integer status, String message) {
        this.token = token;
        this.status = status;
        this.message = message;
    }
}