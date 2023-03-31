package com.shop.user.service;

import com.shop.user.dto.*;
import com.shop.user.exception.model.NotFoundOrganizationException;
import com.shop.user.exception.model.ProductDoesNotExistException;
import com.shop.user.model.product.Product;

import java.util.Map;
import java.util.Set;

public interface ProductService {
    void registerAProduct(ProductDTO productDto) throws NotFoundOrganizationException;

    Set<Product> getAllRegisteredProducts();

    String makeEvaluationAndReview(ReviewAndEvaluationDTO productDto2) throws ProductDoesNotExistException;

}
