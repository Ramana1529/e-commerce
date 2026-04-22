package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // ✅ Create
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    // ✅ Get all
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    // ✅ Update
    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody Category category,
                                   @PathVariable Long id) {
        return categoryService.updateCategory(category, id);
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategoryById(id);
    }
}