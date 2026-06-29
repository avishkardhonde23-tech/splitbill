package com.example.splitbill.repository;

import com.example.splitbill.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {
    Page<ExpenseEntity> findByGroupId(Long groupId, Pageable pageable);
}
