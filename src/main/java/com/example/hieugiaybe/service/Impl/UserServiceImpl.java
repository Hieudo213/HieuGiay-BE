package com.example.hieugiaybe.service.Impl;

import com.example.hieugiaybe.dto.UserDto;
import com.example.hieugiaybe.exception.EmptyDataException;
import com.example.hieugiaybe.security.entities.ForgotPassword;
import com.example.hieugiaybe.security.entities.RefreshToken;
import com.example.hieugiaybe.security.entities.User;
import com.example.hieugiaybe.security.entities.UserRole;
import com.example.hieugiaybe.security.repository.RefreshTokenRepository;
import com.example.hieugiaybe.security.repository.UserRepository;
import com.example.hieugiaybe.service.ImageService;
import com.example.hieugiaybe.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final ImageService imageService;
    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${project.image}")
    private String path;

    public UserServiceImpl(ImageService imageService, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User checkingUser =
                userRepository.findByEmail(email).orElseThrow(()-> new EmptyDataException("Email does not existy"));
        UserDto response = new UserDto(
                checkingUser.getName(),
                checkingUser.getEmail(),
                checkingUser.getRealUsername(),
                checkingUser.getPhone(),
                checkingUser.getAddress(),
                checkingUser.getImage(),
                checkingUser.getRole()
        );
        return response;
    }

    @Override
    public UserDto updateUserByEmail(String email, UserDto userDto) {
        User checkingUser =
                userRepository.findByEmail(email).orElseThrow(()-> new EmptyDataException("Email does not existy"));
        Integer userId = checkingUser.getUserId();
        String password = checkingUser.getPassword();
        RefreshToken refreshToken = checkingUser.getRefreshToken();
        ForgotPassword forgotPassword = checkingUser.getForgotPassword();
        User updatedUser = new User(
                userId,
                userDto.getName(),
                userDto.getUsername(),
                userDto.getEmail(),
                password,
                userDto.getPhone(),
                userDto.getAddress(),
                refreshToken,
                userDto.getRole(),
                userDto.getImage(),
                forgotPassword,
                null
        );
        User savedUser = userRepository.save(updatedUser);
        UserDto response = new UserDto(
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRealUsername(),
                savedUser.getPhone(),
                savedUser.getAddress(),
                savedUser.getImage(),
                savedUser.getRole()
        );

        return response;
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserDto> response = new ArrayList<>();
        for (User user: userList){
            UserDto userDto = new UserDto(
                    user.getName(),
                    user.getEmail(),
                    user.getRealUsername(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getImage(),
                    user.getRole()
            );
            response.add(userDto);
        }
        return response;
    }

    @Override
    public UserDto updateImageUserByEmail(String email, MultipartFile file) throws IOException {
        User checkingUser =
                userRepository.findByEmail(email).orElseThrow(()-> new EmptyDataException("Email does not existy"));
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            Files.deleteIfExists(Paths.get(path + File.separator + checkingUser.getImage()));
        }
        String uploadFileName = imageService.uploadFile(path, file);
        checkingUser.setImage(uploadFileName);
        User savedUser = userRepository.save(checkingUser);
        UserDto response = new UserDto(
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRealUsername(),
                savedUser.getPhone(),
                savedUser.getAddress(),
                savedUser.getImage(),
                savedUser.getRole()
        );
        return response;
    }

    @Override
    public String deleteUserByEmail(String email) {
        User checkingUser =
                userRepository.findByEmail(email).orElseThrow(()-> new EmptyDataException("Email does not exist!"));
        String checkedEmail = checkingUser.getEmail();
        Integer id = checkingUser.getUserId();
        RefreshToken checkingRefresh = refreshTokenRepository.findByUserId(id).orElseThrow(()-> new EmptyDataException("Email does not exist!"));
        checkingRefresh.setUser(null);
        refreshTokenRepository.deleteById(checkingRefresh.getTokenId());
        userRepository.deleteById(id);
        return "Deleted User with email: " + checkedEmail;
    }
}
