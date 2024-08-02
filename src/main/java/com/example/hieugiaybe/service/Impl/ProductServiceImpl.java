package com.example.hieugiaybe.service.Impl;

import com.example.hieugiaybe.dto.BrandDto;
import com.example.hieugiaybe.dto.CategoryDto;
import com.example.hieugiaybe.dto.ProductDto;
import com.example.hieugiaybe.dto.ProductPageResponse;
import com.example.hieugiaybe.dto.update.ProductUpdateDto;
import com.example.hieugiaybe.entities.Brand;
import com.example.hieugiaybe.entities.Category;
import com.example.hieugiaybe.entities.Product;
import com.example.hieugiaybe.exception.EmptyDataException;
import com.example.hieugiaybe.exception.FileExistsException;
import com.example.hieugiaybe.exception.NotFoundException;
import com.example.hieugiaybe.exception.ProductNotFoundException;
import com.example.hieugiaybe.repository.BrandRepository;
import com.example.hieugiaybe.repository.CategoryRepository;
import com.example.hieugiaybe.repository.ProductRepository;
import com.example.hieugiaybe.service.ImageService;
import com.example.hieugiaybe.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;


    public ProductServiceImpl(ProductRepository productRepository, ImageService imageService, BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Value("${project.image}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public ProductDto addProduct(ProductDto productDto, MultipartFile file) throws IOException {
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistsException("File already exists! Please enter another file name!");
        }
        String uploadFileName = imageService.uploadFile(path, file);
        productDto.setImage(uploadFileName);
        productDto.setManufacturingDate(new Date());
        Product product = new Product(
                productDto.getId(),
                productDto.getTitle(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getImage(),
                productDto.getRating(),
                productDto.getQuantity(),
                productDto.getManufacturingDate(),
                productDto.getDiscount(),
                null,
                null,
                null,
                null,
                null
        );


        Product savedProduct = productRepository.save(product);
        String imageUrl = baseUrl + "/file/" + uploadFileName;
        ProductDto response = new ProductDto(
                savedProduct.getId(),
                savedProduct.getTitle(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getImage(),
                savedProduct.getRating(),
                savedProduct.getQuantity(),
                savedProduct.getManufacturingDate(),
                savedProduct.getDiscount(),
                imageUrl
        );
        return response;
    }

    @Override
    public ProductUpdateDto getProductById(Integer id) {
        Optional<Product> checkingProduct = productRepository.findById(id);
        if (checkingProduct.isEmpty()) {
            throw new ProductNotFoundException("Product does not exist!");
        }
        Product checkedProduct = checkingProduct.get();
        String imageUrl = baseUrl + "/file/" + checkedProduct.getImage();
        ProductUpdateDto response = new ProductUpdateDto(
                checkedProduct.getId(),
                checkedProduct.getTitle(),
                checkedProduct.getDescription(),
                checkedProduct.getPrice(),
                checkedProduct.getImage(),
                checkedProduct.getRating(),
                checkedProduct.getQuantity(),
                checkedProduct.getManufacturingDate(),
                checkedProduct.getDiscount(),
                imageUrl,
                checkedProduct.getBrand(),
                checkedProduct.getCategory(),
                checkedProduct.getColors(),
                checkedProduct.getSizes()
        );
        return response;
    }

    @Override
    public List<ProductUpdateDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductUpdateDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductUpdateDto productDto = new ProductUpdateDto(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImage(),
                    product.getRating(),
                    product.getQuantity(),
                    product.getManufacturingDate(),
                    product.getDiscount(),
                    imageUrl,
                    product.getBrand(),
                    product.getCategory(),
                    product.getColors(),
                    product.getSizes()
            );
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public ProductUpdateDto insertBrandAndCategoryIntoProductById(Integer productId,
                                                                  Integer brandId,
                                                                  Integer categoryId) {
        Product checkingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist!"));
        Brand checkingBrand = brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand does not exist!"));
        Category checkingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category does not exist!"));
        checkingProduct.setBrand(checkingBrand);
        checkingProduct.setCategory(checkingCategory);
        String imageUrl = baseUrl + "/file/" + checkingProduct.getImage();
        Product savedProduct = productRepository.save(checkingProduct);
        ProductUpdateDto response = new ProductUpdateDto(
                savedProduct.getId(),
                savedProduct.getTitle(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getImage(),
                savedProduct.getRating(),
                savedProduct.getQuantity(),
                savedProduct.getManufacturingDate(),
                savedProduct.getDiscount(),
                imageUrl,
                savedProduct.getBrand(),
                savedProduct.getCategory(),
                savedProduct.getColors(),
                savedProduct.getSizes()
        );
        return response;
    }

    @Override
    public ProductUpdateDto updateProductById(Integer productId,
                                              Integer brandId,
                                              Integer categoryId,
                                              ProductDto productDto) {
        Product checkingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist!"));
        Brand checkingBrand = brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand does not exist!"));
        Category checkingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category does not exist!"));
        checkingProduct.setId(productDto.getId());
        checkingProduct.setTitle(productDto.getTitle());
        checkingProduct.setDescription(productDto.getDescription());
        checkingProduct.setPrice(productDto.getPrice());
        checkingProduct.setImage(checkingProduct.getImage());
        checkingProduct.setRating(productDto.getRating());
        checkingProduct.setQuantity(productDto.getQuantity());
        checkingProduct.setManufacturingDate(checkingProduct.getManufacturingDate());
        checkingProduct.setDiscount(productDto.getDiscount());
        checkingProduct.setBrand(checkingBrand);
        checkingProduct.setCategory(checkingCategory);
        String imageUrl = baseUrl + "/file/" + checkingProduct.getImage();
        Product savedProduct = productRepository.save(checkingProduct);
        ProductUpdateDto response = new ProductUpdateDto(
                savedProduct.getId(),
                savedProduct.getTitle(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getImage(),
                savedProduct.getRating(),
                savedProduct.getQuantity(),
                savedProduct.getManufacturingDate(),
                savedProduct.getDiscount(),
                imageUrl,
                savedProduct.getBrand(),
                savedProduct.getCategory(),
                savedProduct.getColors(),
                savedProduct.getSizes()
        );
        return response;
    }

    @Override
    public ProductUpdateDto updateProductImageById(Integer id, MultipartFile file) throws IOException {
        Product checkingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist!"));
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            Files.deleteIfExists(Paths.get(path + File.separator + checkingProduct.getImage()));
        }
        String uploadFileName = imageService.uploadFile(path, file);
        checkingProduct.setImage(uploadFileName);
        Product savedProduct = productRepository.save(checkingProduct);
        String imageUrl = baseUrl + "/file/" + uploadFileName;
        ProductUpdateDto response = new ProductUpdateDto(
                savedProduct.getId(),
                savedProduct.getTitle(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getImage(),
                savedProduct.getRating(),
                savedProduct.getQuantity(),
                savedProduct.getManufacturingDate(),
                savedProduct.getDiscount(),
                imageUrl,
                savedProduct.getBrand(),
                savedProduct.getCategory(),
                savedProduct.getColors(),
                savedProduct.getSizes()
        );
        return response;
    }



    @Override
    public String deleteProductById(Integer id) throws IOException {
        Product checkingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product does not exist!"));
        checkingProduct.setBrand(null);
        checkingProduct.setCategory(null);
        Files.deleteIfExists(Paths.get(path + File.separator + checkingProduct.getImage()));
        productRepository.save(checkingProduct);
        Integer productId = checkingProduct.getId();
        productRepository.deleteById(id);
        return "Product deleted with id: " + productId;
    }

    @Override
    public ProductPageResponse getAllProductsWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Product> productPages = productRepository.findAll(pageable);
        List<Product> products = productPages.getContent();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductDto productDto = new ProductDto(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImage(),
                    product.getRating(),
                    product.getQuantity(),
                    product.getManufacturingDate(),
                    product.getDiscount(),
                    imageUrl
            );
            productDtos.add(productDto);
        }
        return new ProductPageResponse(productDtos, pageNumber,pageSize,productPages.getTotalPages(),
                productPages.getTotalElements(),productPages.isLast());
    }

    @Override
    public ProductPageResponse getALlProductsWithPaginationAndSorting(Integer pageNumber,
                                                                      Integer pageSize,
                                                                      String sortBy,
                                                                      String dir) {
        Sort sort = dir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPages = productRepository.findAll(pageable);
        List<Product> products = productPages.getContent();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductDto productDto = new ProductDto(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImage(),
                    product.getRating(),
                    product.getQuantity(),
                    product.getManufacturingDate(),
                    product.getDiscount(),
                    imageUrl
            );
            productDtos.add(productDto);
        }
        return new ProductPageResponse(productDtos, pageNumber, pageSize, productPages.getTotalPages(),
                productPages.getTotalElements(),productPages.isLast());
    }

    @Override
    public List<ProductDto> findProductByTitle(String title) {
        List<Product> checkingProductList = productRepository.findByTitleContainingIgnoreCase(title);
        if (checkingProductList.isEmpty()){
            throw new EmptyDataException("There's not Product contains title  with " + title);
        }
        List<ProductDto> response = new ArrayList<>();
        for (Product product: checkingProductList){
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductDto newProduct = new ProductDto(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImage(),
                    product.getRating(),
                    product.getQuantity(),
                    product.getManufacturingDate(),
                    product.getDiscount(),
                    imageUrl
            );
            response.add(newProduct);
        }
        return response;
    }

    @Override
    public BrandDto getBrandById(Integer brandId, Integer pageNumber, Integer pageSize) {
        Brand checkingBrand = brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand does not exist!"));
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Product> productPages = productRepository.findByBrand(checkingBrand,pageable);
        List<Product> products = productPages.getContent();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductDto productDto = new ProductDto(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImage(),
                    product.getRating(),
                    product.getQuantity(),
                    product.getManufacturingDate(),
                    product.getDiscount(),
                    imageUrl
            );
            productDtos.add(productDto);
        }
        BrandDto response = new BrandDto(
                checkingBrand.getId(),
                checkingBrand.getTitle(),
                checkingBrand.getDescription(),
                new ProductPageResponse(productDtos, pageNumber,pageSize,productPages.getTotalPages(),
                        productPages.getTotalElements(),productPages.isLast())
        );
        return response;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId, Integer pageNumber, Integer pageSize) {
        Category checkingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category does not exist!"));
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Product> productPages = productRepository.findByCategory(checkingCategory,pageable);
        List<Product> products = productPages.getContent();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            String imageUrl = baseUrl + "/file/" + product.getImage();
            ProductDto productDto = new ProductDto(
                    product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getImage(),
                    product.getRating(),
                    product.getQuantity(),
                    product.getManufacturingDate(),
                    product.getDiscount(),
                    imageUrl
            );
            productDtos.add(productDto);
        }
        CategoryDto response = new CategoryDto(
                checkingCategory.getId(),
                checkingCategory.getTitle(),
                checkingCategory.getDescription(),
                new ProductPageResponse(productDtos, pageNumber,pageSize,productPages.getTotalPages(),
                        productPages.getTotalElements(),productPages.isLast())
        );
        return response;
    }


}
