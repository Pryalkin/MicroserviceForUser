package com.shop.user.exception.model;

public class EmailExistException extends Exception{
    public EmailExistException(String message) {
        super(message);
    }
}
