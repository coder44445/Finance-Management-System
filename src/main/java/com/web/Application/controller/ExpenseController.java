package com.web.Application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.web.Application.entity.Expense;
import com.web.Application.service.ExpenseService;

@Slf4j
@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    // Save a new expense (POST)
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Expense expense) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (expense == null) {
            log.error("Expense data is missing");
            return new ResponseEntity<>("Expense data is missing", HttpStatus.BAD_REQUEST);
        }

        try {

            expenseService.saveExpense(expense, authentication.getName());
            return new ResponseEntity<>("Expense saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {

            log.error("Error saving expense: {}", e.getMessage());
            return new ResponseEntity<>("An error occurred while saving the expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing expense (PUT)
    @PutMapping("/{expenseId}")
    public ResponseEntity<?> update(@RequestBody Expense updatedExpense, @PathVariable Long expenseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (updatedExpense == null || expenseId == null) {
            log.error("Expense data or ID is missing");
            return new ResponseEntity<>("Expense data or ID is missing", HttpStatus.BAD_REQUEST);
        }

        try {
            expenseService.updateExpense(updatedExpense, expenseId, authentication.getName());
            return new ResponseEntity<>("Expense updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating expense with ID {}: {}", expenseId, e.getMessage());
            return new ResponseEntity<>("An error occurred while updating the expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete an expense (DELETE)
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<?> delete(@PathVariable Long expenseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (expenseId == null) {
            log.error("Expense ID is missing");
            return new ResponseEntity<>("Expense ID is missing", HttpStatus.BAD_REQUEST);
        }

        try {
            expenseService.deleteExpense(expenseId, authentication.getName());
            return new ResponseEntity<>("Expense deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting expense with ID {}: {}", expenseId, e.getMessage());
            return new ResponseEntity<>("An error occurred while deleting the expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
