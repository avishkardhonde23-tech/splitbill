package com.example.splitbill.service;

import com.example.splitbill.dto.ExpenseRequest;
import com.example.splitbill.dto.ItemRequest;
import com.example.splitbill.dto.SplitRequest;
import com.example.splitbill.entity.ExpenseEntity;
import com.example.splitbill.entity.ExpenseSplitEntity;
import com.example.splitbill.entity.GroupMember;
import com.example.splitbill.repository.ExpenseRepository;
import com.example.splitbill.repository.ExpenseSplitRepository;
import com.example.splitbill.repository.GroupMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.splitbill.enums.SplitType.*;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ExpenseSplitRepository expenseSplitRepository;

    @Transactional
    public ExpenseEntity addExpense(ExpenseRequest request) {
        ExpenseEntity expense = new ExpenseEntity();

        expense.setGroupId(request.getGroupId());
        expense.setPaidBy(request.getPaidBy());
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setSplitType(request.getSplitType());

        ExpenseEntity savedExpense = expenseRepository.save(expense);

        switch (savedExpense.getSplitType()) {

            case EQUAL:
                splitEqual(savedExpense);
                break;

            case EXACT:
                splitExact(savedExpense, request.getSplits());
                break;

            case ITEM_WISE:
                splitItemWise(savedExpense, request.getItems());
                break;

            default:
                throw new RuntimeException("Invalid Split Type");
        }

        return savedExpense;
    }

    public Page<ExpenseEntity> getExpensesByGroup(
            Long groupId,
            int page,
            int size,
            String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending()
        );

        return expenseRepository.findByGroupId(groupId, pageable);
    }
    private void splitEqual(ExpenseEntity expense) {

        List<GroupMember> members =
                groupMemberRepository.findByGroupId(expense.getGroupId());

        double share = expense.getAmount() / members.size();

        for (GroupMember member : members) {

            ExpenseSplitEntity split = new ExpenseSplitEntity();

            split.setExpenseId(expense.getId());
            split.setUserId(member.getUserId());
            split.setAmountOwed(share);

            expenseSplitRepository.save(split);
        }
    }
        private void splitExact(ExpenseEntity expense, List<SplitRequest> splits) {
            double total = 0;

            for (SplitRequest request : splits) {
                total += request.getAmount();
            }

            if (total != expense.getAmount()) {
                throw new RuntimeException("Split amount does not match expense amount");
            }

            for (SplitRequest request : splits) {

                ExpenseSplitEntity split = new ExpenseSplitEntity();

                split.setExpenseId(expense.getId());
                split.setUserId(request.getUserId());
                split.setAmountOwed(request.getAmount());

                expenseSplitRepository.save(split);
            }
        }


    private void splitItemWise(ExpenseEntity expense, List<ItemRequest> items) {

        double total = 0;

        for (ItemRequest item : items) {
            total += item.getPrice();
        }

        if (total != expense.getAmount()) {
            throw new RuntimeException("Item total does not match expense amount");
        }

        for (ItemRequest item : items) {

            double share = item.getPrice() / item.getUserIds().size();

            for (Long userId : item.getUserIds()) {

                ExpenseSplitEntity split = new ExpenseSplitEntity();

                split.setExpenseId(expense.getId());
                split.setUserId(userId);
                split.setAmountOwed(share);

                expenseSplitRepository.save(split);
            }
        }
    }
    @Transactional
    public void deleteExpense(Long expenseId) {

        expenseSplitRepository.deleteByExpenseId(expenseId);

        expenseRepository.deleteById(expenseId);
    }
    @Transactional
    public ExpenseEntity updateExpense(Long expenseId, ExpenseEntity updatedExpense) {

        ExpenseEntity expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setAmount(updatedExpense.getAmount());
        expense.setDescription(updatedExpense.getDescription());

        return expenseRepository.save(expense);
    }
}

