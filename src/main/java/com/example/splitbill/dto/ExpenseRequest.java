package com.example.splitbill.dto;

import com.example.splitbill.enums.SplitType;
import lombok.Data;

import java.util.List;

@Data
public class ExpenseRequest {
    private Long groupId;

    private Long paidBy;

    private Double amount;

    private String description;

    private SplitType splitType;

    private List<SplitRequest> splits;

    private List<ItemRequest> items;
}
