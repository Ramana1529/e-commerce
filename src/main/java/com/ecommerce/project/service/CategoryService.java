package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.CategoryRequestDTO;
import com.ecommerce.project.dto.response.CategoryResponseDTO;
import com.ecommerce.project.entity.Category;
import com.ecommerce.project.exception.BadRequestException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.mapper.CategoryMapper;
import com.ecommerce.project.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        log.info("Creating category: {}", categoryRequestDTO.getName());
        if (categoryRepository.existsByName(categoryRequestDTO.getName())) {
            throw new BadRequestException("Category already exists");
        }

        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        Category savedCategory =  categoryRepository.save(category);
        return CategoryMapper.toDto(savedCategory);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    public CategoryResponseDTO getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return CategoryMapper.toDto(category);
    }

    @Transactional
    public String deleteCategoryById(Long id) {
        log.info("Deleting category id: {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found to delete"));
        categoryRepository.delete(category);
        return "Category deleted successfully";
    }
    @Transactional
    public CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO, Long id) {
        log.info("Updating category id: {}", id);
        Category existing = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        existing.setName(categoryRequestDTO.getName());
        Category saved = categoryRepository.save(existing);
        return CategoryMapper.toDto(saved);

    }
}