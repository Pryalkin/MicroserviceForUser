package com.shop.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchasedSerialProductDTO {

    private String productNumber;
    private LocalDateTime dateOfPurchase;
    @JsonIgnore
    private PurchasedProductDTO purchasedProductDTO;

}
