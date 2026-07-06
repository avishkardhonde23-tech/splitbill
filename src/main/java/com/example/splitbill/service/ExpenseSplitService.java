package com.example.splitbill.service;

import com.example.splitbill.dto.BalanceResponse;
import com.example.splitbill.dto.MemberBalanceResponse;
import com.example.splitbill.dto.SettleRequest;
import com.example.splitbill.entity.ExpenseEntity;
import com.example.splitbill.entity.ExpenseSplitEntity;
import com.example.splitbill.entity.GroupMember;
import com.example.splitbill.entity.UserEntity;
import com.example.splitbill.repository.ExpenseRepository;
import com.example.splitbill.repository.ExpenseSplitRepository;
import com.example.splitbill.repository.GroupMemberRepository;
import com.example.splitbill.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseSplitService {
    private final ExpenseSplitRepository repository;
    private final GroupMemberRepository groupMemberRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseSplitEntity saveSplit(
            ExpenseSplitEntity split) {

        return repository.save(split);
    }

    public List<ExpenseSplitEntity> getSplitsByExpense(Long expenseId) {
        return repository.findByExpenseId(expenseId);
    }

    public List<BalanceResponse> getBalances(Long groupId) {

        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);

        List<BalanceResponse> balances = new ArrayList<>();

        for (GroupMember member : members) {

            List<ExpenseSplitEntity> splits =
                    repository.findByUserId(member.getUserId());

            double total = 0;

            for (ExpenseSplitEntity split : splits) {
                total += split.getAmountOwed();
            }

            balances.add(new BalanceResponse(
                    member.getUserId(),
                    total
            ));
        }

        return balances;
    }

    @Transactional
    public void settle(SettleRequest request) {

        List<ExpenseSplitEntity> splits = repository.findByUserId(request.getFromUserId());

        double remaining = request.getAmount();

        for (ExpenseSplitEntity split : splits) {

            if (remaining <= 0) {
                break;
            }

            double owed = split.getAmountOwed();

            if (owed <= remaining) {
                remaining -= owed;
                split.setAmountOwed(0.0);
            } else {
                split.setAmountOwed(owed - remaining);
                remaining = 0;
            }

            repository.save(split);
        }
    }

    public List<MemberBalanceResponse> getMemberBalances(Long groupId) {

        List<MemberBalanceResponse> balances = new ArrayList<>();

        List<GroupMember> members =
                groupMemberRepository.findByGroupId(groupId);

        List<ExpenseEntity> expenses =
                expenseRepository.findAllByGroupId(groupId);

        for (GroupMember member : members) {

            double paid = 0;

            // Total amount paid by this member
            for (ExpenseEntity expense : expenses) {

                if (expense.getPaidBy().equals(member.getUserId())) {
                    paid += expense.getAmount();
                }
            }

            // Remaining amount this member still owes
            List<ExpenseSplitEntity> splits =
                    repository.findByUserId(member.getUserId());
            System.out.println("User ID: " + member.getUserId());
            System.out.println("Splits found: " + splits.size());

            for (ExpenseSplitEntity split : splits) {
                System.out.println(
                        "Expense: " + split.getExpenseId() +
                                " User: " + split.getUserId() +
                                " Owed: " + split.getAmountOwed()
                );
            }

            double remainingOwed = 0;
            double originalShare = 0;

            for (ExpenseSplitEntity split : splits) {

                remainingOwed += split.getAmountOwed();

                ExpenseEntity expense =
                        expenseRepository.findById(split.getExpenseId()).orElse(null);

                if (expense != null) {

                    List<GroupMember> groupMembers =
                            groupMemberRepository.findByGroupId(expense.getGroupId());

                    originalShare += expense.getAmount() / groupMembers.size();
                }
            }

            double balance = paid - remainingOwed;

            UserEntity user =
                    userRepository.findById(member.getUserId()).orElse(null);

            if (user == null) {
                continue;
            }

            MemberBalanceResponse response = new MemberBalanceResponse();

            response.setMemberName(user.getName());
            response.setPaid(paid);

            // show remaining share instead of original share
            response.setShare(originalShare);

            response.setBalance(balance);

            balances.add(response);
        }

        return balances;
    }
}