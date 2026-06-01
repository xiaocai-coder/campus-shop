package com.campus.backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String username;
    private String nickname;
    private String role;
    private String token;
}