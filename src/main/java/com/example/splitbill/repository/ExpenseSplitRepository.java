package com.example.splitbill.repository;

import com.example.splitbill.entity.ExpenseSplitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplitEntity,Long> {
    List<ExpenseSplitEntity> findByExpenseId(Long expenseId);
    List<ExpenseSplitEntity> findByUserId(Long userId);
    void deleteByExpenseId(Long expenseId);

}
