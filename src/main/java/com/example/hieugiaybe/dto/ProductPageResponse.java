package com.example.hieugiaybe.dto;

import java.util.List;

public record ProductPageResponse(List<ProductDto> productDtos,
                                  Integer pageNumber,
                                  Integer pageSize,
                                  int totalElement,
                                  long totalPages,
                                  boolean isLast) {
}
