package com.web.Application.service;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.stereotype.Service;

import com.web.Application.entity.Expense;
import com.web.Application.entity.User;

@Service
public class BudgetAlertService {


    private static final float WARNING_THRESHOLD = 0.9f;

    private float monthlyExpense;

    public boolean isBudgetExceeded(User user) {
        
        Month currentMonth = LocalDate.now().getMonth();
        int currentYear = LocalDate.now().getYear();
        monthlyExpense = 0;

        // iterating through the expenses in reverse order 
        
        for(int i=user.getExpenses().size()-1;i>=0;i--){

            Expense expense = user.getExpenses().get(i);

            if(expense.getDate().getMonth() == currentMonth && expense.getDate().getYear() == currentYear){
                monthlyExpense += expense.getAmount();
            }else{
                break; // if month ended no need to iterate further so
            }
        }

        if( monthlyExpense >= user.getBudget()){
            return true;
        }

        return false;
    }

    public boolean isBudgetApproaching(User user) {
       
        if(monthlyExpense >= WARNING_THRESHOLD * user.getBudget()){

            return true;
        }

        return false;
    }

    public String getAlertMessage(User user) {
        if (isBudgetExceeded(user)) {
            return "Warning: You have exceeded your budget!";
        } else if (isBudgetApproaching(user)) {
            return "Alert: You are approaching your budget limit.";
        } else {
            return "You are within your budget.";
        }
    }
}
