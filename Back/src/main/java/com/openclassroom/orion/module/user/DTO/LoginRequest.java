package com.openclassroom.orion.module.user.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
