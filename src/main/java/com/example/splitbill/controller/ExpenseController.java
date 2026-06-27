package com.example.splitbill.controller;

import com.example.splitbill.dto.ExpenseRequest;
import com.example.splitbill.entity.ExpenseEntity;
import com.example.splitbill.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ExpenseEntity addExpense(
            @RequestBody ExpenseRequest request){

        return expenseService.addExpense(request);
    }
    @GetMapping("/group/{groupId}")
    public List<ExpenseEntity> getExpensesByGroup(
            @PathVariable Long groupId){

        return expenseService.getExpensesByGroup(groupId);
    }
}
