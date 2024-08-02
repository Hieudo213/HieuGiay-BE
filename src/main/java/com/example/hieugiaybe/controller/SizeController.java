package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.dto.ColorAndSizeDto;
import com.example.hieugiaybe.service.SizeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/size")
public class SizeController {
    private final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorAndSizeDto> getSizeById(@PathVariable  Integer id){
        return ResponseEntity.ok(sizeService.getSizeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ColorAndSizeDto>> getAllSize(){
        return ResponseEntity.ok(sizeService.getAllSize());
    }

    @PostMapping("/add-size")
    public ResponseEntity<ColorAndSizeDto> createNewSize(@RequestBody ColorAndSizeDto colorAndSizeDto){
        ColorAndSizeDto newSize = this.sizeService.createSize(colorAndSizeDto);
        return new ResponseEntity<>(newSize, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ColorAndSizeDto> updateSizeById(@PathVariable Integer id,
                                                          @RequestBody ColorAndSizeDto colorAndSizeDto){
        ColorAndSizeDto response = sizeService.updateSizeById(id, colorAndSizeDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSizeById(@PathVariable Integer id){
        return ResponseEntity.ok(sizeService.deleteSizeById(id));
    }
}
