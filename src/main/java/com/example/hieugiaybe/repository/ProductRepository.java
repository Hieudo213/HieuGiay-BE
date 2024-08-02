package com.example.hieugiaybe.repository;

import com.example.hieugiaybe.entities.Brand;
import com.example.hieugiaybe.entities.Category;
import com.example.hieugiaybe.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByTitleContainingIgnoreCase(String title);

    Page<Product> findByBrand(Brand brand, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);


}
