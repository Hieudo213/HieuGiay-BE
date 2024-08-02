package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.dto.BrandAndCategoryDto;
import com.example.hieugiaybe.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("{id}")
    public ResponseEntity<BrandAndCategoryDto> getBrandById(@PathVariable Integer id){
        BrandAndCategoryDto result = brandService.getBrandById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BrandAndCategoryDto>> getAllBrand(){
        return ResponseEntity.ok(brandService.getAllBrand());
    }

    @PostMapping("/add-brand")
    public ResponseEntity<BrandAndCategoryDto> createNewBrand(@RequestBody BrandAndCategoryDto brandAndCategoryDto){
        BrandAndCategoryDto result = brandService.createBrand(brandAndCategoryDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BrandAndCategoryDto> updateBrandById(@PathVariable Integer id,
                                                               @RequestBody BrandAndCategoryDto brandAndCategoryDto){
        BrandAndCategoryDto result = brandService.updateBrandById(id, brandAndCategoryDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBrandById(@PathVariable Integer id){
        return ResponseEntity.ok(brandService.deleteBrandById(id));
    }


}
