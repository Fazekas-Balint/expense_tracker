package com.example.progkorny.service;

import com.example.progkorny.model.Expense;
import com.example.progkorny.repository.ExpenseRepository;
import com.example.progkorny.model.Category;
import com.example.progkorny.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    private ExpenseRepository expenseRepository;
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        expenseRepository = mock(ExpenseRepository.class);
        expenseService = new ExpenseService(expenseRepository);
    }

    @Test
    void testGetAllExpenses() {
        // GIVEN
        List<Expense> mockList = List.of(
                new Expense(1L, 3000.0, LocalDate.now(), "Pizza", new User(), new Category())
        );
        when(expenseRepository.findAll()).thenReturn(mockList);

        // WHEN
        List<Expense> result = expenseService.getAllExpenses();

        // THEN
        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getDescription());
    }

    @Test
    void testGetExpenseById() {
        // GIVEN
        Expense expense = new Expense(2L, 500.0, LocalDate.now(), "Buszjegy", new User(), new Category());
        when(expenseRepository.findById(2L)).thenReturn(Optional.of(expense));

        // WHEN
        Expense result = expenseService.getExpenseById(2L);

        // THEN
        assertNotNull(result);
        assertEquals("Buszjegy", result.getDescription());
    }

    @Test
    void testCreateExpense() {
        // GIVEN
        Expense input = new Expense(null, 2500.0, LocalDate.now(), "Színház", new User(), new Category());
        Expense saved = new Expense(3L, 2500.0, LocalDate.now(), "Színház", new User(), new Category());
        when(expenseRepository.save(input)).thenReturn(saved);

        // WHEN
        Expense result = expenseService.createExpense(input);

        // THEN
        assertNotNull(result);
        assertEquals("Színház", result.getDescription());
    }

    @Test
    void testUpdateExpense() {
        // GIVEN
        Expense existing = new Expense(4L, 800.0, LocalDate.now(), "Kávé", new User(), new Category());
        Expense updated = new Expense(null, 1200.0, LocalDate.now(), "Kávé latte", new User(), new Category());

        when(expenseRepository.findById(4L)).thenReturn(Optional.of(existing));
        when(expenseRepository.save(any())).thenReturn(
                new Expense(4L, 1200.0, LocalDate.now(), "Kávé latte", new User(), new Category())
        );

        // WHEN
        Expense result = expenseService.updateExpense(4L, updated);

        // THEN
        assertEquals("Kávé latte", result.getDescription());
        assertEquals(1200.0, result.getAmount());
    }

    @Test
    void testDeleteExpense() {
        // GIVEN
        Long id = 5L;
        doNothing().when(expenseRepository).deleteById(id);

        // WHEN
        expenseService.deleteExpense(id);

        // THEN
        verify(expenseRepository, times(1)).deleteById(id);
    }
}