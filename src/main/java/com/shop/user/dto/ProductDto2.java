package com.shop.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto2 {
    @NotNull
    private String productId;
    private String review;
    private Integer evaluation;

}
