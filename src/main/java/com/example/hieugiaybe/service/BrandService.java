package com.example.hieugiaybe.service;

import com.example.hieugiaybe.dto.BrandAndCategoryDto;
import com.example.hieugiaybe.entities.Brand;
import com.example.hieugiaybe.exception.EmptyDataException;
import com.example.hieugiaybe.exception.NotFoundException;
import com.example.hieugiaybe.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public BrandAndCategoryDto getBrandById(Integer id){
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isEmpty()){
            throw new NotFoundException("Brand does not exist!");
        }
        Brand checkedBrand = brand.get();
        BrandAndCategoryDto response = new BrandAndCategoryDto(
                checkedBrand.getId(),
                checkedBrand.getTitle(),
                checkedBrand.getDescription()
        );
        return response;
    }

    public List<BrandAndCategoryDto> getAllBrand(){
        List<Brand> checkedListBrand = brandRepository.findAll();
        if (checkedListBrand.isEmpty()){
            throw new EmptyDataException("List Brand is empty!");
        }
        List<BrandAndCategoryDto> listBrands = new ArrayList<>();
        for (Brand brand: checkedListBrand){
            BrandAndCategoryDto newBrand = new BrandAndCategoryDto(
                    brand.getId(),
                    brand.getTitle(),
                    brand.getDescription()
            );
            listBrands.add(newBrand);
        }

        return listBrands;
    }

    public BrandAndCategoryDto createBrand(BrandAndCategoryDto brandDto){
        Brand newBrand = new Brand(
                brandDto.getId(),
                brandDto.getTitle(),
                brandDto.getDescription(),
                null
        );
        Brand savedBrand = brandRepository.save(newBrand);
        BrandAndCategoryDto response = new BrandAndCategoryDto(
                savedBrand.getId(),
                savedBrand.getTitle(),
                savedBrand.getDescription()
        );
        return response;
    }
    public BrandAndCategoryDto updateBrandById(Integer id, BrandAndCategoryDto brandAndCategoryDto){
        Brand checkingBrand = brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Brand does not exist!"));
        checkingBrand.setId(brandAndCategoryDto.getId());
        checkingBrand.setTitle(brandAndCategoryDto.getTitle());
        checkingBrand.setDescription(brandAndCategoryDto.getDescription());
        Brand savedBrand = brandRepository.save(checkingBrand);
        BrandAndCategoryDto response = new BrandAndCategoryDto(
                savedBrand.getId(),
                savedBrand.getTitle(),
                savedBrand.getDescription()
        );
        return response;
    }

    public String deleteBrandById(Integer id){
        Brand checkingBrand = brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Brand does not exist!"));
        Integer brandId = checkingBrand.getId();
        brandRepository.deleteById(brandId);
        return "Brand deleted with id: " + brandId;
    }


}
