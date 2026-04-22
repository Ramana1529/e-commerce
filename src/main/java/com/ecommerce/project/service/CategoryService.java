package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public String deleteCategoryById(Long id) {
        Category category = getById(id); // ensures existence
        categoryRepository.delete(category);
        return "Category deleted successfully";
    }

    public Category updateCategory(Category category, Long id) {
        Category existing = getById(id);

        existing.setName(category.getName());

        return categoryRepository.save(existing);
    }
}