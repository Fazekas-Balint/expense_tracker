package com.example.progkorny.controller;

import com.example.progkorny.model.Expense;
import com.example.progkorny.model.User;
import com.example.progkorny.service.CategoryService;
import com.example.progkorny.service.ExpenseService;
import com.example.progkorny.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/expenses")
public class ExpenseWebController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final UserService userService;

    /**
     * Constructor for ExpenseWebController.
     *
     * @param expenseService the expense service
     * @param categoryService the category service
     * @param userService the user service
     */
    public ExpenseWebController(final ExpenseService expenseService,
                                final CategoryService categoryService,
                                final UserService userService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    /**
     * GET: Kiadás szerkesztése form
     *
     * @param model the model
     * @return the expense edit form
     */
    @GetMapping
    public String listExpenses(final HttpSession session, final Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("expenses", expenseService.getExpensesByUser(user));
        return "expenses";
    }

    /**
     * GET: Kiadás szerkesztése form
     *
     * @param model a modell
     * @return a kiadás szerkesztési űrlapja
     */
    @GetMapping("/new")
    public String showExpenseForm(final Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("users", userService.getAllUsers());
        return "expense-form";
    }
    /**
     * POST: Új kiadás mentése
     *
     * @param expense a kiadás
     * @param model a modell
     * @return a kiadások listája
     */
    @PostMapping("/expenses")
    public String addExpense(@ModelAttribute final Expense expense,
                             final Model model) {
        try {
            expenseService.createExpense(expense);
        } catch (Exception e) {
            model.addAttribute("error",
                    "Nem sikerült menteni a kiadást: " + e.getMessage());
            return "expense-form";
        }
        return "redirect:/web/expenses";
    }

    /**
     * GET: Kiadás szerkesztése form
     *
     * @return a kiadás szerkesztési űrlapja
     */
    @PostMapping
    public String saveExpense(@RequestParam("categoryId") final Long categoryId,
                              @ModelAttribute final Expense expense,
                              final HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        expense.setCategory(categoryService.getCategoryById(categoryId));
        expense.setUser(user);
        expenseService.createExpense(expense);
        return "redirect:/expenses";
    }

    /**
     * GET: Kiadás szerkesztése form.
     */
    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable final Long id, final Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("users", userService.getAllUsers());
        return "expense-form";
    }

    /**
     * POST: Kiadás szerkesztése
     *
     * @param id a kiadás azonosítója
     * @return a kiadások listája
     */
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable final Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }
}
