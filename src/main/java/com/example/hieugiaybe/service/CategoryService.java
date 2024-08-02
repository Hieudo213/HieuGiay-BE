package com.example.hieugiaybe.service;

import com.example.hieugiaybe.dto.BrandAndCategoryDto;
import com.example.hieugiaybe.entities.Brand;
import com.example.hieugiaybe.entities.Category;
import com.example.hieugiaybe.exception.EmptyDataException;
import com.example.hieugiaybe.exception.NotFoundException;
import com.example.hieugiaybe.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public BrandAndCategoryDto getCategoryById(Integer id){
        Optional<Category> checkedCategory = categoryRepository.findById(id);
        if (checkedCategory.isEmpty()){
            throw new NotFoundException("Category does not exist!");
        }
        Category category = checkedCategory.get();
        BrandAndCategoryDto response = new BrandAndCategoryDto(
                category.getId(),
                category.getTitle(),
                category.getDescription()
        );
        return response;
    }

    public List<BrandAndCategoryDto> getAllCategories(){
        List<Category> listCheckedCategories = categoryRepository.findAll();
        if (listCheckedCategories.isEmpty()){
            throw new EmptyDataException("List Category is empty!");
        }
        List<BrandAndCategoryDto> response = new ArrayList<>();
        for (Category category : listCheckedCategories){
            BrandAndCategoryDto categoryDto = new BrandAndCategoryDto(
                    category.getId(),
                    category.getTitle(),
                    category.getDescription()
            );
            response.add(categoryDto);
        }
        return response;
    }

    public BrandAndCategoryDto createCategory(BrandAndCategoryDto brandAndCategoryDto){
        Category newCategory = new Category(
                brandAndCategoryDto.getId(),
                brandAndCategoryDto.getTitle(),
                brandAndCategoryDto.getDescription(),
                null
        );
        Category savedCategory =  categoryRepository.save(newCategory);
        BrandAndCategoryDto response = new BrandAndCategoryDto(
                savedCategory.getId(),
                savedCategory.getTitle(),
                savedCategory.getDescription()
        );
        return response;
    }

    public BrandAndCategoryDto updateCategoryById(Integer id, BrandAndCategoryDto brandAndCategoryDto){
        Category checkingCategory = categoryRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Category does not exist!"));
        checkingCategory.setId(brandAndCategoryDto.getId());
        checkingCategory.setTitle(brandAndCategoryDto.getTitle());
        checkingCategory.setDescription(brandAndCategoryDto.getDescription());
        Category savedCategory = categoryRepository.save(checkingCategory);
        BrandAndCategoryDto response = new BrandAndCategoryDto(
                savedCategory.getId(),
                savedCategory.getTitle(),
                savedCategory.getDescription()
        );
        return response;
    }

    public String deleteCategoryById(Integer id){
        Category checkingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category does not exist!"));
        Integer categoryId = checkingCategory.getId();
        categoryRepository.deleteById(categoryId);
        return "Category deleted with id: " + categoryId;
    }
}
