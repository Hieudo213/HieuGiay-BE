package com.example.hieugiaybe.service;

import com.example.hieugiaybe.dto.UserDto;
import com.example.hieugiaybe.security.util.RegisterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto getUserByEmail(String email);

    UserDto updateUserByEmail(String email, UserDto userDto);

    List<UserDto> getAllUser();

    UserDto updateImageUserByEmail(String email, MultipartFile file) throws IOException;

    String deleteUserByEmail(String email);

}
