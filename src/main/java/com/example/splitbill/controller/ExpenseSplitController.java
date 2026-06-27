package com.example.splitbill.controller;

import com.example.splitbill.dto.BalanceResponse;
import com.example.splitbill.dto.SettleRequest;
import com.example.splitbill.entity.ExpenseSplitEntity;
import com.example.splitbill.service.ExpenseSplitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/splits")
@RequiredArgsConstructor
public class ExpenseSplitController {
    private final ExpenseSplitService service;

    @PostMapping
    public ExpenseSplitEntity addSplit(
            @RequestBody ExpenseSplitEntity split) {

        return service.saveSplit(split);
    }
    @GetMapping("/expense/{expenseId}")
    public List<ExpenseSplitEntity> getSplits(
            @PathVariable Long expenseId) {

        return service.getSplitsByExpense(expenseId);
    }
    @GetMapping("/balance/{groupId}")
    public List<BalanceResponse> getBalances(@PathVariable Long groupId) {
        return service.getBalances(groupId);
    }
    @PostMapping("/settle")
    public String settle(@RequestBody SettleRequest request) {

        service.settle(request);

        return "Settlement Successful";
    }
}
