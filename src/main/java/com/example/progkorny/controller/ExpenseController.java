package com.example.progkorny.controller;

import com.example.progkorny.model.Expense;
import com.example.progkorny.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    /**
     * Expense service for business logic.
     */
    private final ExpenseService service;

    /**
     * Constructor for ExpenseController.
     *
     * @param service the expense service
     */
    public ExpenseController(final ExpenseService service) {
        this.service = service;
    }

    /**
     * Retrieves all expenses.
     *
     * @return a list of all expenses
     */
    @GetMapping
    public List<Expense> getAllExpenses() {
        return service.getAllExpenses();
    }

    /**
     * Retrieves an expense by ID.
     *
     * @param id the ID of the expense
     * @return the expense object, or null if not found
     */
    @GetMapping("/{id}")
    public Expense getExpense(@PathVariable final Long id) {
        return service.getExpenseById(id);
    }

    /**
     * Creates a new expense.
     *
     * @param expense the expense object to create
     * @return the created expense object
     */
    @PostMapping
    public Expense createExpense(@RequestBody final Expense expense) {
        return service.createExpense(expense);
    }

    /**
     * Updates an existing expense.
     *
     * @param id the ID of the expense to update
     * @param expense the updated expense object
     * @return the updated expense object, or null if not found
     */
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable final Long id,
                                 @RequestBody final Expense expense) {
        return service.updateExpense(id, expense);
    }

    /**
     * Deletes an expense by ID.
     *
     * @param id the ID of the expense to delete
     */
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable final Long id) {
        service.deleteExpense(id);
    }
}
