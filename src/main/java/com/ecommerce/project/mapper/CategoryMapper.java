package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.response.CategoryResponseDTO;
import com.ecommerce.project.entity.Category;

public class CategoryMapper {
    public static CategoryResponseDTO toDto(Category category){
        if (category == null) return null;

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
