package com.example.hieugiaybe.dto.update;

import com.example.hieugiaybe.entities.Brand;
import com.example.hieugiaybe.entities.Category;
import com.example.hieugiaybe.entities.Color;
import com.example.hieugiaybe.entities.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ProductUpdateDto {
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

    @JsonIgnoreProperties(value = {"products"},allowSetters = true)
    private Brand brand;

    @JsonIgnoreProperties(value = {"products"},allowSetters = true)
    private Category category;

    @JsonIgnoreProperties(value = {"products"},allowSetters = true)
    private List<Color> colors;

    @JsonIgnoreProperties(value = {"products"},allowSetters = true)
    private List<Size> sizes;
}
