package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.dto.BrandAndCategoryDto;
import com.example.hieugiaybe.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("{id}")
    public ResponseEntity<BrandAndCategoryDto> getCategoryById(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BrandAndCategoryDto>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/add-category")
    public ResponseEntity<BrandAndCategoryDto> createNewCategory(@RequestBody BrandAndCategoryDto brandAndCategoryDto){
        BrandAndCategoryDto newCategory = categoryService.createCategory(brandAndCategoryDto);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BrandAndCategoryDto> updateCategoryById(@PathVariable Integer id,
                                                                  @RequestBody BrandAndCategoryDto brandAndCategoryDto){
        BrandAndCategoryDto result = categoryService.updateCategoryById(id,brandAndCategoryDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.deleteCategoryById(id));
    }
}
