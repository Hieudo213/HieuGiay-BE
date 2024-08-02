package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.dto.UserDto;
import com.example.hieugiaybe.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(@PathVariable String email, @RequestBody UserDto userDto){
        return ResponseEntity.ok(this.userService.updateUserByEmail(email, userDto));
    }

    @PutMapping("/image/{email}")
    public ResponseEntity<UserDto> updateUserImageByEmail(@PathVariable String email, @RequestPart MultipartFile file)throws IOException{
        UserDto response = this.userService.updateImageUserByEmail(email, file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.deleteUserByEmail(email));
    }

}
