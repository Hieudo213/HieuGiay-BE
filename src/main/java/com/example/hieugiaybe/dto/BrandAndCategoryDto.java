package com.example.hieugiaybe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class BrandAndCategoryDto {
    private int id;
    private String title;
    private String description;
}
