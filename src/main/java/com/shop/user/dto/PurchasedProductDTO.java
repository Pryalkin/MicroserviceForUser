package com.shop.user.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PurchasedProductDTO {

    private String prodId;
    private String name;
    private String description;
    private String organization;
    private Double price;
    private Set<CharacteristicDTO> characteristicDtos = new HashSet<>();

}
