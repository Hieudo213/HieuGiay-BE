package com.example.hieugiaybe.controller;

import com.example.hieugiaybe.dto.BrandDto;
import com.example.hieugiaybe.dto.CategoryDto;
import com.example.hieugiaybe.dto.ProductDto;
import com.example.hieugiaybe.dto.ProductPageResponse;
import com.example.hieugiaybe.dto.update.ProductUpdateDto;
import com.example.hieugiaybe.exception.EmptyFileException;
import com.example.hieugiaybe.service.ProductService;
import com.example.hieugiaybe.utils.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private ProductDto convertToProductDto(String productDtoObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(productDtoObject, ProductDto.class);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-product")
    public ResponseEntity<ProductDto> addProductHandler(@RequestPart MultipartFile file,
                                                        @RequestPart String productDto) throws IOException {
        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty!Please send another file.");
        }
        ProductDto dto = convertToProductDto(productDto);
        return new ResponseEntity<>(productService.addProduct(dto, file), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductUpdateDto> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductUpdateDto>> getAllProductsHandler() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/update/insert")
    public ResponseEntity<ProductUpdateDto> insertBrandAndCategoryIntoProductById(@RequestParam Integer productId,
                                                                                  @RequestParam Integer brandId,
                                                                                  @RequestParam Integer categoryId) {
        ProductUpdateDto updatedProduct =
                productService.insertBrandAndCategoryIntoProductById(productId, brandId, categoryId);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductUpdateDto> updateProductById(@RequestParam Integer productId,
                                                              @RequestParam Integer brandId,
                                                              @RequestParam Integer categoryId,
                                                              @RequestBody ProductDto productDto) {
        ProductUpdateDto updatedProduct =
                productService.updateProductById(productId, brandId, categoryId, productDto);
        return ResponseEntity.ok(updatedProduct);
    }


    @PutMapping("/update/image/{id}")
    public ResponseEntity<ProductUpdateDto> updateProductImageById(@PathVariable Integer id,
                                                                   @RequestPart MultipartFile file) throws IOException {
        ProductUpdateDto updatedProduct =
                productService.updateProductImageById(id, file);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer id) throws IOException {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

    @GetMapping("/allProductPage")
    public ResponseEntity<ProductPageResponse> getProductWithPagination(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ){
        return ResponseEntity.ok(productService.getAllProductsWithPagination(pageNumber,pageSize));
    }

    @GetMapping("/allProductPageSort")
    public ResponseEntity<ProductPageResponse> getProductWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR,required = false) String dir
    ){
        return ResponseEntity.ok(productService.getALlProductsWithPaginationAndSorting(pageNumber,pageSize,sortBy,dir));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductByTitle(@RequestParam String title){
        return ResponseEntity.ok(productService.findProductByTitle(title));
    }

    @GetMapping("/brand")
    public ResponseEntity<BrandDto> getProductByBrand(
            @RequestParam Integer brandId,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ){
        return ResponseEntity.ok(productService.getBrandById(brandId,pageNumber,pageSize));
    }

    @GetMapping("/category")
    public ResponseEntity<CategoryDto> getProductByCategory(
            @RequestParam Integer categoryId,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ){
        return ResponseEntity.ok(productService.getCategoryById(categoryId, pageNumber, pageSize));
    }
}
