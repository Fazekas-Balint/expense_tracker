package com.example.progkorny.service;

import com.example.progkorny.model.Expense;
import com.example.progkorny.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    /**
     * The repository for managing expenses.
     */
    private final ExpenseRepository expenseRepo;

    /**
     * Constructor for ExpenseService.
     *
     * @param expenseRepo the expense repository
     */
    public ExpenseService(final ExpenseRepository expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    /**
     * Retrieves all expenses.
     *
     * @return a list of all expenses
     */
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    /**
     * Retrieves an expense by ID.
     *
     * @param id the ID of the expense
     * @return the expense object, or null if not found
     */
    public Expense getExpenseById(final Long id) {
        return expenseRepo.findById(id).orElse(null);
    }

    /**
     * Creates a new expense.
     *
     * @param expense the expense object to create
     * @return the created expense object
     */
    public Expense createExpense(final Expense expense) {
        return expenseRepo.save(expense);
    }

    /**
     * Updates an existing expense.
     *
     * @param id the ID of the expense to update
     * @param updatedExpense the updated expense object
     * @return the updated expense object, or null if not found
     */
    public Expense updateExpense(final Long id, final Expense updatedExpense) {
        Expense expense = expenseRepo.findById(id).orElse(null);
        if (expense != null) {
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());
            expense.setDescription(updatedExpense.getDescription());
            expense.setUser(updatedExpense.getUser());
            expense.setCategory(updatedExpense.getCategory());
            return expenseRepo.save(expense);
        }
        return null;
    }

    /**
     * Deletes an expense by ID.
     *
     * @param id the ID of the expense to delete
     */
    public void deleteExpense(final Long id) {
        expenseRepo.deleteById(id);
    }
}
