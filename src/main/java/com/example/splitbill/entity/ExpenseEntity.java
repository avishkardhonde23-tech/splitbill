package com.example.splitbill.entity;

import com.example.splitbill.enums.SplitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long groupId;

    private Long paidBy;

    private Double amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private SplitType splitType;
}
