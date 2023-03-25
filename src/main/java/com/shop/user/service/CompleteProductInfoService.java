package com.shop.user.service;

import com.shop.user.dto.ProductDto;
import com.shop.user.exception.model.NotFoundOrganizationException;

public interface CompleteProductInfoService {
    void registerAProduct(ProductDto productDto) throws NotFoundOrganizationException;
}
