package com.example.splitbill.dto;

import com.example.splitbill.entity.ExpenseEntity;
import com.example.splitbill.entity.GroupMember;
import lombok.Data;

import java.util.List;

@Data
public class DashboardResponse {
    private List<GroupMember> members;

    private List<ExpenseResponse> expenses;

    private List<BalanceResponse> balances;
}
