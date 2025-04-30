package com.example.demo.service;

import com.example.demo.dto.request.BudgetRequest;
import com.example.demo.dto.response.BudgetResponse;
import com.example.demo.exception.NotFoundException;

public interface BudgetCalculationService {

    BudgetResponse calculateBudget(BudgetRequest budgetRequest) throws NotFoundException;
}
