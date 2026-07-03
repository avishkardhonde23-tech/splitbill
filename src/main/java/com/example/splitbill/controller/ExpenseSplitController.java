package com.example.splitbill.controller;

import com.example.splitbill.dto.BalanceResponse;
import com.example.splitbill.dto.MemberBalanceResponse;
import com.example.splitbill.dto.SettleRequest;
import com.example.splitbill.entity.ExpenseSplitEntity;
import com.example.splitbill.service.ExpenseSplitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.splitbill.dto.MemberBalanceResponse;
import java.util.List;

@Tag(name = "Split API", description = "Expense split and settlement")
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

    @Operation(summary = "Get member balances")
    @GetMapping("/balances/{groupId}")
    public List<MemberBalanceResponse> getMemberBalances(
            @PathVariable Long groupId) {

        return service.getMemberBalances(groupId);
    }
    @Operation(summary = "Settle balance")
    @PostMapping("/settle")
    public String settle(@RequestBody SettleRequest request) {

        service.settle(request);

        return "Settlement Successful";
    }
}
