package com.shop.user.service;

import com.shop.user.exception.model.EmailExistException;
import com.shop.user.exception.model.UserNotFoundException;
import com.shop.user.exception.model.UsernameExistException;
import com.shop.user.model.user.User;
import jakarta.mail.MessagingException;

public interface UserService {
    User register(String username, String email) throws MessagingException, UserNotFoundException, EmailExistException, UsernameExistException;
    User findByUsername(String username) throws UsernameExistException;

    User getUser(String username);
}
