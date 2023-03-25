package com.shop.user.controller;

import com.shop.user.exception.ExceptionHandling;
import com.shop.user.exception.model.EmailExistException;
import com.shop.user.exception.model.UserNotFoundException;
import com.shop.user.exception.model.UsernameExistException;
import com.shop.user.model.user.UserPrincipal;
import com.shop.user.model.user.User;
import com.shop.user.service.UserService;
import com.shop.user.utility.JWTTokenProvider;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.shop.user.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController extends ExceptionHandling {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/registration")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, EmailExistException, MessagingException, UsernameExistException {
        User newUser = userService.register(user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) throws UsernameExistException {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/getUser/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userService.getUser(username);
        return new ResponseEntity<>(user, OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

}
