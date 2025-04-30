package com.example.demo.controller;

import com.example.demo.dto.request.BudgetRequest;
import com.example.demo.dto.response.BudgetResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.BudgetCalculationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BudgetController {

    private BudgetCalculationService budgetCalculationService;

    @PostMapping("/budget")
    public BudgetResponse calculateBudget(@RequestBody BudgetRequest budgetRequest)throws NotFoundException{
        return budgetCalculationService.calculateBudget(budgetRequest);
    }
}
