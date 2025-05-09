package com.example.progkorny.service;

import com.example.progkorny.model.Category;
import com.example.progkorny.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    /**
     * The repository for managing categories.
     */
    private final CategoryRepository categoryRepo;

    /**
     * Constructor for CategoryService.
     *
     * @param categoryRepo the category repository
     */
    public CategoryService(final CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    /**
     * Retrieves a category by ID.
     *
     * @param id the ID of the category
     * @return the category object, or null if not found
     */
    public Category getCategoryById(final Long id) {
        return categoryRepo.findById(id).orElse(null);
    }

    /**
     * Creates a new category.
     *
     * @param category the category object to create
     * @return the created category object
     */
    public Category createCategory(final Category category) {
        return categoryRepo.save(category);
    }

    /**
     * Updates an existing category.
     *
     * @param id the ID of the category to update
     * @param updatedCategory the updated category object
     * @return the updated category object, or null if not found
     */
    public Category updateCategory(final Long id,
                                   final Category updatedCategory) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category != null) {
            category.setName(updatedCategory.getName());
            return categoryRepo.save(category);
        }
        return null;
    }

    /**
     * Deletes a category by ID.
     *
     * @param id the ID of the category to delete
     */
    public void deleteCategory(final Long id) {
        categoryRepo.deleteById(id);
    }

    /**
     * Finds a category by name.
     *
     * @param name the name of the category
     * @return the category object, or null if not found
     */
    public Category findByName(final String name) {
        return categoryRepo.findByName(name).orElse(null);
    }
}
