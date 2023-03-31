package com.shop.user.service.impl;

import com.shop.user.model.product.Serial;
import com.shop.user.repository.SerialRepo;
import com.shop.user.service.SerialService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class SerialServiceImpl implements SerialService {

    private final SerialRepo serialRepo;

    @Override
    public Set<Serial> put(long amount) {
        Set<Serial> serials = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            Serial serial = new Serial();
            String productNumber = generateProductNumber();
            while (serialRepo.findByProductNumber(productNumber).isPresent())
                productNumber = generateProductNumber();
            serial.setProductNumber(generateProductNumber());
            serials.add(serial);
        }
        return serials;
    }

    private String generateProductNumber() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
