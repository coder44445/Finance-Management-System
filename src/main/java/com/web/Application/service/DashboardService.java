package com.web.Application.service;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.Application.Placeholders.Placeholder;
import com.web.Application.dto.DashboardDTO;
import com.web.Application.entity.Expense;
import com.web.Application.entity.User;
import com.web.Application.repository.ExpenseRepository;

@Service
public class DashboardService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    // Get total spending
    public Double getTotalSpending(User user) {
        return expenseRepository.findByUser(user).stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
    
    // Get spending by category
    public Map<String, Double> getSpendingByCategory(User user) {
        Map<String, Double> spendingByCategory = new HashMap<>();
        List<Expense> expenses = expenseRepository.findByUser(user);
        
        for (Expense expense : expenses) {
            String categoryName = expense.getCategory();
            spendingByCategory.put(categoryName, 
                    spendingByCategory.getOrDefault(categoryName, 0.0) + expense.getAmount());
        }
        
        return spendingByCategory;
    }
    
    // Get spending over time (daily/weekly/monthly)
    public Map<String, Double> getSpendingOverTime(User user, Placeholder period) {
        Map<String, Double> spendingOverTime = new HashMap<>();
        List<Expense> expenses = expenseRepository.findByUser(user);
        
        for (Expense expense : expenses) {
            String key = getPeriodKey(expense.getDate(), period);
            spendingOverTime.put(key, 
                    spendingOverTime.getOrDefault(key, 0.0) + expense.getAmount());
        }
        
        return spendingOverTime;
    }
    
    private String getPeriodKey(LocalDate date,Placeholder period) {

        switch (period) {
            case Placeholder.YEARLY:
                return date.getYear() + "";
            case Placeholder.MONTHLY:
                return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
            case Placeholder.WEEKLY:
                int weekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                return date.getYear() + "-W" + weekOfYear;
            case Placeholder.DAILY:
            default:
                return date.toString();
        }
    }
    
    // dashboard data by period
    public DashboardDTO getDashboardByPeriod(User user,Placeholder period) {
        DashboardDTO dashboard = new DashboardDTO();

        dashboard.setSpendingOverTime(getSpendingOverTime(user,period));
        
        return dashboard;
    }

    public DashboardDTO getDashboardYearly(User user) {
        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setSpendingOverTime(getSpendingOverTime(user,Placeholder.YEARLY));
        
        return dashboard;
    }

    public DashboardDTO getDashboardByCategory(User user) {

        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setSpendingByCategory(getSpendingByCategory(user));
        
        return dashboard;
    }


    // Combine all dashboard data
    public DashboardDTO getAllTypesDashboard(User user) {
        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setTotalSpending(getTotalSpending(user));
        dashboard.setSpendingByCategory(getSpendingByCategory(user));
        dashboard.setSpendingOverTime(getSpendingOverTime(user,Placeholder.YEARLY));
        
        return dashboard;
    }
}

