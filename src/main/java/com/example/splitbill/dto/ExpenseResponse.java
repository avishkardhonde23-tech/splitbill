package com.example.splitbill.dto;

import lombok.Data;

@Data
public class ExpenseResponse {
    private Long id;

    private String description;

    private Double amount;

    private String paidBy;
}
