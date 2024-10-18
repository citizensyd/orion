package com.openclassroom.orion.module.user.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponse {
    private Long userId;
    private String username;
    private String message;
}
