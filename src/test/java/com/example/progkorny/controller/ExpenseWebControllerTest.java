package com.example.progkorny.controller;

import com.example.progkorny.model.Category;
import com.example.progkorny.model.Expense;
import com.example.progkorny.model.User;
import com.example.progkorny.service.CategoryService;
import com.example.progkorny.service.ExpenseService;
import com.example.progkorny.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExpenseWebControllerTest {

    @Mock
    private ExpenseService expenseService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private ExpenseWebController expenseWebController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListExpenses_UserLoggedIn() {
        // GIVEN
        User loggedInUser = new User();
        loggedInUser.setId(1L);
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        when(expenseService.getExpensesByUser(loggedInUser)).thenReturn(Collections.emptyList());

        // WHEN
        String result = expenseWebController.listExpenses(session, model);

        // THEN
        verify(model, times(1)).addAttribute(eq("expenses"), anyList());
        assertEquals("expenses", result);
    }

    @Test
    void testListExpenses_UserNotLoggedIn() {
        // GIVEN
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        // WHEN
        String result = expenseWebController.listExpenses(session, model);

        // THEN
        assertEquals("redirect:/login", result);
    }

    @Test
    void testShowExpenseForm() {
        // GIVEN
        when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // WHEN
        String result = expenseWebController.showExpenseForm(model);

        // THEN
        verify(model, times(1)).addAttribute(eq("expense"), any(Expense.class));
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
        verify(model, times(1)).addAttribute(eq("users"), anyList());
        assertEquals("expense-form", result);
    }

    @Test
    void testAddExpense_Success() {
        // GIVEN
        Expense expense = new Expense();

        // WHEN
        String result = expenseWebController.addExpense(expense, model);

        // THEN
        verify(expenseService, times(1)).createExpense(expense);
        assertEquals("redirect:/web/expenses", result);
    }

    @Test
    void testAddExpense_Failure() {
        // GIVEN
        Expense expense = new Expense();
        doThrow(new RuntimeException("Test Exception")).when(expenseService).createExpense(expense);

        // WHEN
        String result = expenseWebController.addExpense(expense, model);

        // THEN
        verify(model, times(1)).addAttribute(eq("error"), anyString());
        assertEquals("expense-form", result);
    }

    @Test
    void testSaveExpense_UserLoggedIn() {
        // GIVEN
        Long categoryId = 1L;
        User loggedInUser = new User();
        loggedInUser.setId(1L);
        Category category = new Category();
        category.setId(categoryId);

        Expense expense = new Expense();
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        // WHEN
        String result = expenseWebController.saveExpense(categoryId, expense, session);

        // THEN
        ArgumentCaptor<Expense> expenseCaptor = ArgumentCaptor.forClass(Expense.class);
        verify(expenseService, times(1)).createExpense(expenseCaptor.capture());

        Expense capturedExpense = expenseCaptor.getValue();
        assertEquals(loggedInUser, capturedExpense.getUser());
        assertEquals(category, capturedExpense.getCategory());
        assertEquals("redirect:/expenses", result);
    }

    @Test
    void testSaveExpense_UserNotLoggedIn() {
        // GIVEN
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        // WHEN
        String result = expenseWebController.saveExpense(1L, new Expense(), session);

        // THEN
        assertEquals("redirect:/login", result);
    }

    @Test
    void testEditExpense() {
        // GIVEN
        Long expenseId = 1L;
        Expense expense = new Expense();
        when(expenseService.getExpenseById(expenseId)).thenReturn(expense);
        when(categoryService.getAllCategories()).thenReturn(Collections.emptyList());
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // WHEN
        String result = expenseWebController.editExpense(expenseId, model);

        // THEN
        verify(model, times(1)).addAttribute("expense", expense);
        verify(model, times(1)).addAttribute(eq("categories"), anyList());
        verify(model, times(1)).addAttribute(eq("users"), anyList());
        assertEquals("expense-form", result);
    }

    @Test
    void testDeleteExpense() {
        // GIVEN
        Long expenseId = 1L;

        // WHEN
        String result = expenseWebController.deleteExpense(expenseId);

        // THEN
        verify(expenseService, times(1)).deleteExpense(expenseId);
        assertEquals("redirect:/expenses", result);
    }
}