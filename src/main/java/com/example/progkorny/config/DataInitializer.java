package com.example.progkorny.config;

import com.example.progkorny.model.Category;
import com.example.progkorny.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    private static final List<String> DEFAULT_CATEGORIES = List.of(
            "Bevásárlás", "Utalás", "Közlekedés", "Otthon",
            "Vendéglátás", "Egyéb", "Adomány", "Egészség",
            "Készpénzfelvétel", "Ruházat", "Szállás és utazás",
            "Számlák", "Szórakozás"
    );

    /**
     * Initializes default categories in the database.
     *
     * @param categoryService the category service
     * @return a CommandLineRunner to execute on application startup
     */
    @Bean
    public CommandLineRunner initCategories(
            final CategoryService categoryService) {
        return args -> {
            for (String name : DEFAULT_CATEGORIES) {
                if (categoryService.findByName(name) == null) {
                    Category category = new Category();
                    category.setName(name);
                    categoryService.createCategory(category);
                }
            }
        };
    }
}
