package com.web.Application.dto;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DashboardDTO {

    private Double totalSpending;
    private Map<String, Double> spendingByCategory;  // example : key = category name, value = total spending
    private Map<String, Double> spendingOverTime;    // example : key = date, value = total spending
    

}
