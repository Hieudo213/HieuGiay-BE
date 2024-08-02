package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.dto.ColorAndSizeDto;
import com.example.hieugiaybe.service.ColorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/color")
public class ColorController {
    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ColorAndSizeDto> getColorById(@PathVariable Integer id){
        return ResponseEntity.ok(colorService.getColorById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ColorAndSizeDto>> getAllColor(){
        return ResponseEntity.ok(colorService.getAllColor());
    }

    @PostMapping("/add-color")
    public ResponseEntity<ColorAndSizeDto> createNewColor(@RequestBody ColorAndSizeDto colorAndSizeDto){
        ColorAndSizeDto newColor = colorService.createColor(colorAndSizeDto);
        return new ResponseEntity<>(newColor, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ColorAndSizeDto> updateColorById (@PathVariable Integer id,
                                                           @RequestBody ColorAndSizeDto colorAndSizeDto){
        ColorAndSizeDto result = colorService.updateColorById(id, colorAndSizeDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteColorById(@PathVariable Integer id){
        return ResponseEntity.ok(colorService.deleteCategoryById(id));
    }


}
