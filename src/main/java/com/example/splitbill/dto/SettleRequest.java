package com.example.splitbill.dto;

import lombok.Data;

@Data
public class SettleRequest {

    private Long fromUserId;

    private Long toUserId;

    private Double amount;
}
