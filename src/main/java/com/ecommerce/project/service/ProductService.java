package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.ProductRequestDTO;
import com.ecommerce.project.dto.response.ProductResponseDTO;
import com.ecommerce.project.entity.Category;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.mapper.ProductMapper;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        Product product = ProductMapper.toEntity(dto, category);
        product.setCategory(category);

        return ProductMapper.toDto(productRepository.save(product));
    }

    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toDto(product);
    }

    public ProductResponseDTO updateProduct(ProductRequestDTO dto, Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(category);

        return ProductMapper.toDto(productRepository.save(product));
    }

    public String deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.deleteById(id);
        return "Product deleted successfully";
    }

    public List<ProductResponseDTO> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public List<ProductResponseDTO> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }
}