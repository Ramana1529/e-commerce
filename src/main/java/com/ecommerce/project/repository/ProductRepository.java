package com.ecommerce.project.repository;

import com.ecommerce.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

}
