package com.raxrot.back.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String username;
    private List<String> roles;
    private String jwtToken;
}
