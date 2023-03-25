package com.shop.user.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PurchaseDto {

    private Set<String> productsId;
    private String username;
}
