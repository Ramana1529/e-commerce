package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.ProductRequestDTO;
import com.ecommerce.project.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);

    void deleteProduct(Long id);

    Page<ProductResponseDTO> getAll(Pageable pageable);

    Page<ProductResponseDTO> search(String name, Pageable pageable);

    Page<ProductResponseDTO> getByCategory(Long categoryId, Pageable pageable);
}
