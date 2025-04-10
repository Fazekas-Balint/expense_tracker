package com.example.progkorny.service;

import com.example.progkorny.model.Expense;
import com.example.progkorny.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepo;

    public ExpenseService(ExpenseRepository expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepo.findById(id).orElse(null);
    }

    public Expense createExpense(Expense expense) {
        return expenseRepo.save(expense);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
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

    public void deleteExpense(Long id) {
        expenseRepo.deleteById(id);
    }
}