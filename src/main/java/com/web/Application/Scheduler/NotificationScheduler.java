package com.web.Application.Scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.web.Application.entity.User;
import com.web.Application.repository.UserRepository;
import com.web.Application.service.BudgetAlertService;
import com.web.Application.service.NotificationService;

@Service
public class NotificationScheduler {

    @Autowired
    private BudgetAlertService budgetAlertService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 9 * * *") // Schedule at 9 AM every day
    public void checkUserBudgets() {
        List<User> users = userRepository.findAll(); // Get all users
        for (User user : users) {
            String alertMessage = budgetAlertService.getAlertMessage(user);
            if (!alertMessage.equals("You are within your budget.")) {
                notificationService.sendBudgetNotification(user, alertMessage); // Send email alert
            }
        }
    }
}
