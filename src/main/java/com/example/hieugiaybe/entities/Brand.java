package com.example.hieugiaybe.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,length = 200)
    @NotBlank(message = "Please provide brand's title!")
    private String title;
    @Column(nullable = false,length = 200)
    @NotBlank(message = "Please provide brand's description!")
    private String description;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Product> products;


}
