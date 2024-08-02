package com.example.hieugiaybe.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide product's title!")
    private String title;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide product's description!")
    private String description;

    @Column(nullable = false, length = 200)
    @NotNull
    private Integer price;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide product's image url!")
    private String image;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide product's rating!")
    private String rating;

    @Column(nullable = false, length = 200)
    @NotNull
    private Integer quantity;

    @Column(nullable = false, length = 200)
    @NotNull
    private Date manufacturingDate;

    @Column(nullable = false, length = 200)
    @NotNull
    private Integer discount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "products")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Color> colors = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Size> sizes = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Orders> orders = new ArrayList<>();


}
