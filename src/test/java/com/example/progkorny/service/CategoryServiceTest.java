package com.example.progkorny.service;

import com.example.progkorny.model.Category;
import com.example.progkorny.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void testGetAllCategories() {
        // GIVEN - előkészítjük az elvárt adatokat
        List<Category> mockList = List.of(new Category(1L, "Utazás"));
        when(categoryRepository.findAll()).thenReturn(mockList);

        // WHEN - meghívjuk a tesztelendő metódust
        List<Category> result = categoryService.getAllCategories();

        // THEN - ellenőrizzük az eredményt
        assertEquals(1, result.size());
        assertEquals("Utazás", result.get(0).getName());
    }

    @Test
    void testGetCategoryById() {
        // GIVEN
        Category category = new Category(2L, "Szórakozás");
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));

        // WHEN
        Category result = categoryService.getCategoryById(2L);

        // THEN
        assertNotNull(result);
        assertEquals("Szórakozás", result.getName());
    }

    @Test
    void testCreateCategory() {
        // GIVEN
        Category input = new Category(null, "Egészség");
        Category saved = new Category(3L, "Egészség");
        when(categoryRepository.save(input)).thenReturn(saved);

        // WHEN
        Category result = categoryService.createCategory(input);

        // THEN
        assertNotNull(result);
        assertEquals("Egészség", result.getName());
    }

    @Test
    void testUpdateCategory() {
        // GIVEN
        Category existing = new Category(4L, "Sport");
        Category updated = new Category(null, "Sport & Wellness");
        when(categoryRepository.findById(4L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(any())).thenReturn(new Category(4L, "Sport & Wellness"));

        // WHEN
        Category result = categoryService.updateCategory(4L, updated);

        // THEN
        assertEquals("Sport & Wellness", result.getName());
    }

    @Test
    void testDeleteCategory() {
        // GIVEN
        Long id = 5L;
        doNothing().when(categoryRepository).deleteById(id);

        // WHEN
        categoryService.deleteCategory(id);

        // THEN
        verify(categoryRepository, times(1)).deleteById(id);
    }
}
