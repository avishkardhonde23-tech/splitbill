package com.example.splitbill.dto;

import lombok.Data;

import java.util.List;
@Data
public class ItemRequest {
    private String itemName;

    private Double price;

    private List<Long> userIds;

}
