package com.shop.user.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PurchaseDTO {

    private Map<String, Integer> products;
    private String buyer;

}
