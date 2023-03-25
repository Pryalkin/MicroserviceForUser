package com.shop.user.dto;

import com.shop.user.model.product.Characteristics;
import com.shop.user.model.product.Keyword;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProductDto {

    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String organization;
    @NotNull
    private Set<Characteristics> characteristics;
    @NotNull
    private Double price;
    @NotNull
    private Set<Keyword> keywords;
    @NotNull
    private Long amount;
    @NotNull
    private String username;
}
