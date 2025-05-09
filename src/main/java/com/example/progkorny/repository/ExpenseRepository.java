package com.example.progkorny.repository;

import com.example.progkorny.model.Expense;
import com.example.progkorny.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}
