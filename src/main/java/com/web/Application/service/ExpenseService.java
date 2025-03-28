package com.web.Application.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.web.Application.entity.Expense;
import com.web.Application.entity.User;
import com.web.Application.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j  // This annotation automatically provides a logger for the class
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserService userService;

    public void saveExpense(Expense expense, String userName) {
        try {
            if (expense.getDate() == null) {
                expense.setDate(LocalDate.now());
            }

            // Fetch the user to link to the expense
            User user = userService.findByUserName(userName);
            if (user == null) {
                log.error("User not found: {}", userName);
                throw new IllegalArgumentException("User not found");
            }

            // Set the user on the expense
            expense.setUser(user);

            // Save the expense (this will automatically update the relationship in the database)
            expenseRepository.save(expense);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while saving expense for user: {}", userName, e);
            throw e;
        } catch (Exception e) {
            log.error("Error saving expense for user: {}", userName, e.getMessage());
            throw new RuntimeException("Error saving expense", e);
        }
    }

    public void deleteExpense(Long expenseId, String userName) {
        try {
            // Find the expense by ID
            Expense expense = expenseRepository.findById(expenseId).orElse(null);

            if (expense != null) {
                // Remove the expense from the user's list of expenses
                User user = userService.findByUserName(userName);
                user.getExpenses().remove(expense);
                userService.saveUser(user);

                // Delete the expense from the database
                expenseRepository.delete(expense);

            } else {
                log.warn("Expense with ID {} not found for user: {}", expenseId, userName);
            }
        } catch (Exception e) {
            log.error("Error deleting expense with ID {} for user: {}", expenseId, userName, e.getMessage());
            throw new RuntimeException("Error deleting expense", e);
        }
    }

    public void updateExpense(Expense updatedExpense, Long expenseId, String userName) {
        try {
            // Find the expense by ID
            Expense existingExpense = expenseRepository.findById(expenseId).orElse(null);

            if (existingExpense != null) {
                // Update the fields of the existing expense
                existingExpense.setAmount(updatedExpense.getAmount());
                existingExpense.setCategory(updatedExpense.getCategory());
                existingExpense.setDescription(updatedExpense.getDescription());

                // Optionally, if the date is null in the updatedExpense, you can keep the existing date or update it to the current date
                if (updatedExpense.getDate() != null) {
                    existingExpense.setDate(updatedExpense.getDate());
                } else {
                    existingExpense.setDate(LocalDate.now());  // Default to current date if null
                }

                // Save the updated expense
                expenseRepository.save(existingExpense);

                // Optionally, update the user's expenses (in case the expense object changed in any other way)
                User user = userService.findByUserName(userName);
                user.getExpenses().remove(existingExpense);  // Remove the old expense
                user.getExpenses().add(existingExpense);     // Add the updated expense
                userService.saveUser(user);

            } else {
                log.warn("Expense with ID {} not found for user: {}", expenseId, userName);
            }
        } catch (Exception e) {
            log.error("Error updating expense with ID {} for user: {}", expenseId, userName, e.getMessage());
            throw new RuntimeException("Error updating expense", e);
        }
    }
}
