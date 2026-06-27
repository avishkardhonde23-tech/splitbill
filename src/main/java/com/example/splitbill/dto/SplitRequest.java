package com.example.splitbill.dto;

import lombok.Data;

@Data
public class SplitRequest {

    private Long userId;

    private Double amount;
}
