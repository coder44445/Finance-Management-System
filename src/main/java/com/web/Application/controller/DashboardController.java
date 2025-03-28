package com.web.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.Application.Placeholders.Placeholder;
import com.web.Application.dto.DashboardDTO;
import com.web.Application.entity.User;
import com.web.Application.service.DashboardService;
import com.web.Application.service.UserService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboardDefault(@PathVariable String periodString) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName(authentication.getName());  

       
        DashboardDTO dashboard = dashboardService.getDashboardYearly(user);
        
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/Categroy")
    public ResponseEntity<DashboardDTO> getDashboardByCategory() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName(authentication.getName());  

       
        DashboardDTO dashboard = dashboardService.getDashboardByCategory(user);
        
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("period/{period}")
    public ResponseEntity<DashboardDTO> getDashboardByPeriod(@PathVariable String periodString) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName(authentication.getName());  

        Placeholder periodPlaceholder = Placeholder.YEARLY;
        
        if(periodString.equals("MONTHLY")){

            periodPlaceholder = Placeholder.MONTHLY;
        }else if(periodString.equals("WEEKLY")){

            periodPlaceholder = Placeholder.WEEKLY;
        }else if(periodString.equals("DAILY")){

            periodPlaceholder = Placeholder.DAILY;
        }

        DashboardDTO dashboard = dashboardService.getDashboardByPeriod(user,periodPlaceholder);
        
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/All")
    public ResponseEntity<DashboardDTO> getCombinedDashboard() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName(authentication.getName());  

       

        DashboardDTO dashboard = dashboardService.getAllTypesDashboard(user);
        
        return ResponseEntity.ok(dashboard);
    }


}
