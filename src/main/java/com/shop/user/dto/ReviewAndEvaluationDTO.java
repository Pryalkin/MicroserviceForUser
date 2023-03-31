package com.shop.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewAndEvaluationDTO {

    @NotNull
    private String productId;
    private String review;
    private Integer evaluation;
    private String username;

}
