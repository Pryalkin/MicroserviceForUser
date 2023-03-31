package com.shop.user.service;

import com.shop.user.dto.PurchaseDTO;
import com.shop.user.dto.PurchasedProductDTO;
import com.shop.user.dto.PurchasedSerialProductDTO;
import com.shop.user.exception.model.NotEnoughMoneyException;
import com.shop.user.exception.model.UserNotFoundException;

import java.util.Map;
import java.util.Set;

public interface PurchaseService {
    void buy(PurchaseDTO productDto) throws NotEnoughMoneyException, UserNotFoundException;

    Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>> get(String username);

    Map<Boolean, Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>>> returnTheProduct(Set<String> productNumbers, String username);
}
