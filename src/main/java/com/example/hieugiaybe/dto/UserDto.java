package com.example.hieugiaybe.dto;

import com.example.hieugiaybe.security.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String name;
    private String email;
    private String username;
    private String phone;
    private String address;
    private String image;
    private UserRole role;
}
