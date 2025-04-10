package com.example.progkorny.service;

import com.example.progkorny.model.Category;
import com.example.progkorny.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }

    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category != null) {
            category.setName(updatedCategory.getName());
            return categoryRepo.save(category);
        }
        return null;
    }

    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }
}
