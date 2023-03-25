package com.shop.user.service;

import com.shop.user.dto.ProductDto;
import com.shop.user.dto.ProductDto2;
import com.shop.user.exception.model.NotFoundOrganizationException;
import com.shop.user.exception.model.ProductDoesNotExistException;
import com.shop.user.model.product.Product;

import java.util.Set;

public interface ProductService {
    void registerAProduct(ProductDto productDto) throws NotFoundOrganizationException;

    Set<Product> getAllRegisteredProducts();

    String makeEvaluationAndReview(ProductDto2 productDto2) throws ProductDoesNotExistException;
}
