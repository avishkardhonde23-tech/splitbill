package com.example.splitbill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberBalanceResponse {
    private String memberName;

    private Double paid;

    private Double share;

    private Double balance;

}
