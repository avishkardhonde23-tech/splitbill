package com.example.splitbill.service;

import com.example.splitbill.dto.BalanceResponse;
import com.example.splitbill.dto.DashboardResponse;
import com.example.splitbill.dto.ExpenseResponse;
import com.example.splitbill.entity.ExpenseEntity;
import com.example.splitbill.entity.GroupMember;
import com.example.splitbill.repository.GroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final GroupMemberRepository groupMemberRepository;
    private final ExpenseService expenseService;
    private final ExpenseSplitService expenseSplitService;

    public DashboardResponse getDashboard(Long groupId) {

        DashboardResponse response = new DashboardResponse();

        List<GroupMember> members =
                groupMemberRepository.findByGroupId(groupId);

        Page<ExpenseResponse> expensePage =
                expenseService.getExpensesByGroup(groupId, 0, 100, "id");

        List<ExpenseResponse> expenses = expensePage.getContent();

        List<BalanceResponse> balances =
                expenseSplitService.getBalances(groupId);

        response.setMembers(members);
        response.setExpenses(expenses);
        response.setBalances(balances);

        return response;
    }
}
