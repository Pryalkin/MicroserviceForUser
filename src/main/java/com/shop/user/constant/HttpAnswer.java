package com.shop.user.constant;

import com.shop.user.model.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpAnswer {

    public static final String APPLICATION_SENT = "Application sent";
    public static final String GOODS_SUCCESSFULLY_PURCHASED = "Goods successfully purchased";
    public static final String EVALUATION_THE_PRODUCT = "You have already rated the product before. ";
    public static final String PRODUCT_RATED = "Product rated. ";
    public static final String YOU_HAVE_ALREADY_LEFT_A_REVIEW = "You have already left a review";
    public static final String PRODUCT_REVIEW = "You have successfully submitted a review";

    public static ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());
        return new ResponseEntity<>(body, httpStatus);
    }
}
