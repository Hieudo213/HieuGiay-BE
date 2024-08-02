package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandle (@RequestPart MultipartFile file) throws IOException {
        String uploadFileName = imageService.uploadFile(path, file);
        return ResponseEntity.ok("File uploaded : " + uploadFileName);
    }

    @GetMapping(value = "/{fileName}")
    public void serveFileHandle(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resourceFile = imageService.getResourceFile(path, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}
