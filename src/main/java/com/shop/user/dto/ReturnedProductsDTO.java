package com.shop.user.dto;

import com.shop.user.dto.PurchasedProductDTO;
import com.shop.user.dto.PurchasedSerialProductDTO;
import com.shop.user.model.product.Serial;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class ReturnedProductsDTO {

    private Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>> returnedProducts = new HashMap<>();
    private Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>> productsNotReturned = new HashMap<>();
    private String message;

}
