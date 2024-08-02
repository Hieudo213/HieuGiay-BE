package com.example.hieugiaybe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    private Integer id;

    @NotBlank(message = "Please provide product's title!")
    private String title;

    @NotBlank(message = "Please provide product's description!")
    private String description;

    @NotNull
    private Integer price;

    @NotBlank(message = "Please provide product's image url!")
    private String image;

    @NotBlank(message = "Please provide product's rating!")
    private String rating;

    @NotNull
    private Integer quantity;

    @NotNull
    private Date manufacturingDate;

    @NotNull
    private Integer discount;

    @NotBlank(message = "Please provide movie's image url!")
    private String imageUrl;
}
