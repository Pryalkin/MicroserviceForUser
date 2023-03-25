package com.shop.user.service;

import com.shop.user.dto.PurchaseDto;
import com.shop.user.exception.model.NotEnoughMoneyException;
import com.shop.user.exception.model.UserNotFoundException;
import com.shop.user.model.Purchase;

import java.util.Set;

public interface PurchaseService {
    void buy(PurchaseDto productDto) throws NotEnoughMoneyException, UserNotFoundException;

    Set<Purchase> get(String username);
}
