package com.shop.user.exception.model;

public class ProductDoesNotExistException extends Exception{
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}