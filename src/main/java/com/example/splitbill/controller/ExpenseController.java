package com.example.splitbill.controller;

import com.example.splitbill.dto.ExpenseRequest;
import com.example.splitbill.entity.ExpenseEntity;
import com.example.splitbill.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
@Tag(name = "Expense API",description = "API for managing expenses")
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @Operation(summary = "Create a new expense")
    @PostMapping
    public ExpenseEntity addExpense(
            @Valid @RequestBody ExpenseRequest request){

        return expenseService.addExpense(request);
    }
    @Operation(summary = "Get all expenses of a group")
    @GetMapping("/group/{groupId}")
    public Page<ExpenseEntity> getExpensesByGroup(
            @PathVariable Long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return expenseService.getExpensesByGroup(groupId, page, size, sortBy);
    }

    @Operation(summary = "Delete an expense")
    @DeleteMapping("/{expenseId}")
    public String deleteExpense(@PathVariable Long expenseId) {

        expenseService.deleteExpense(expenseId);

        return "Expense deleted successfully";
    }

    @Operation(summary = "Update an existing expense")
    @PutMapping("/{expenseId}")
    public ExpenseEntity updateExpense(
            @PathVariable Long expenseId,
            @RequestBody ExpenseEntity updatedExpense) {

        return expenseService.updateExpense(expenseId, updatedExpense);
    }
}
