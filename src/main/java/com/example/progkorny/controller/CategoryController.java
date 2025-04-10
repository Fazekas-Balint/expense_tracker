package com.example.progkorny.controller;

import com.example.progkorny.model.Category;
import com.example.progkorny.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    /**
     * Category service for business logic.
     */
    private final CategoryService service;

    /**
     * Constructor for CategoryController.
     *
     * @param service the category service
     */
    public CategoryController(final CategoryService service) {
        this.service = service;
    }

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return service.getAllCategories();
    }

    /**
     * Retrieves a category by ID.
     *
     * @param id the ID of the category
     * @return the category object, or null if not found
     */
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable final Long id) {
        return service.getCategoryById(id);
    }

    /**
     * Creates a new category.
     *
     * @param category the category object to create
     * @return the created category object
     */
    @PostMapping
    public Category createCategory(@RequestBody final Category category) {
        return service.createCategory(category);
    }

    /**
     * Updates an existing category.
     *
     * @param id the ID of the category to update
     * @param category the updated category object
     * @return the updated category object, or null if not found
     */
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable final Long id,
                                   @RequestBody final Category category) {
        return service.updateCategory(id, category);
    }

    /**
     * Deletes a category by ID.
     *
     * @param id the ID of the category to delete
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable final Long id) {
        service.deleteCategory(id);
    }
}
