package com.example.hieugiaybe.service;

import com.example.hieugiaybe.dto.BrandDto;
import com.example.hieugiaybe.dto.CategoryDto;
import com.example.hieugiaybe.dto.ProductDto;
import com.example.hieugiaybe.dto.ProductPageResponse;
import com.example.hieugiaybe.dto.update.ProductUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto, MultipartFile file) throws IOException;
    ProductUpdateDto getProductById(Integer id);

    List<ProductUpdateDto> getAllProducts();

    ProductUpdateDto insertBrandAndCategoryIntoProductById(Integer productId,
                                                           Integer brandId,
                                                           Integer categoryId);
    ProductUpdateDto updateProductById(Integer productId,
                                       Integer brandId,
                                       Integer categoryId,
                                       ProductDto productDto);
    ProductUpdateDto updateProductImageById (Integer id, MultipartFile file) throws IOException;

    String deleteProductById(Integer id) throws IOException;

    ProductPageResponse getAllProductsWithPagination(Integer pageNumber, Integer pageSize);
    ProductPageResponse getALlProductsWithPaginationAndSorting(Integer pageNumber, Integer pageSize,
                                                               String sortBy, String dir);

    List<ProductDto> findProductByTitle(String title);

    BrandDto getBrandById(Integer brandId, Integer pageNumber, Integer pageSize);

    CategoryDto getCategoryById(Integer categoryId, Integer pageNumber, Integer pageSize);
}

