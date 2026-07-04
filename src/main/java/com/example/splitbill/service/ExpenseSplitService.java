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
            ExpenseSplitEntity split){

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

        double totalExpense = 0;

        for (ExpenseEntity expense : expenses) {
            totalExpense += expense.getAmount();
        }
        double sharePerPerson = totalExpense / members.size();
        for (GroupMember member : members) {

            double paid = 0;

            for (ExpenseEntity expense : expenses) {

                if (expense.getPaidBy().equals(member.getUserId())) {
                    paid += expense.getAmount();
                }

            }

            double balance = paid - sharePerPerson;

            MemberBalanceResponse response = new MemberBalanceResponse();

            UserEntity user = userRepository.findById(member.getUserId())
                    .orElse(null);

            if (user == null) {
                continue;
            }

            response.setMemberName(user.getName());
            response.setPaid(paid);
            response.setShare(sharePerPerson);
            response.setBalance(balance);

            balances.add(response);

        }

        return balances;
    }
}
