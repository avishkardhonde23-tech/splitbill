package com.example.splitbill.dto;

import com.example.splitbill.enums.SplitType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ExpenseRequest {
    @NotNull
    private Long groupId;

    @NotNull
    private Long paidBy;

    @NotNull
    @Min(1)
    private Double amount;

    @NotBlank
    private String description;

    private SplitType splitType;

    private List<SplitRequest> splits;

    private List<ItemRequest> items;
}
