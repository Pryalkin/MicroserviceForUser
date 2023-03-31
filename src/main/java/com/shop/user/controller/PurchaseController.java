package com.shop.user.controller;

import com.shop.user.constant.HttpAnswer;
import com.shop.user.dto.*;
import com.shop.user.exception.ExceptionHandling;
import com.shop.user.exception.model.NoRightException;
import com.shop.user.exception.model.NotEnoughMoneyException;
import com.shop.user.exception.model.UserNotFoundException;
import com.shop.user.model.HttpResponse;
import com.shop.user.service.PurchaseService;
import com.shop.user.utility.JWTTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.shop.user.constant.HttpAnswer.GOODS_SUCCESSFULLY_PURCHASED;
import static com.shop.user.controller.security.ValidUsernameSecurity.checkUsernameForValidity;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user/purchase")
@AllArgsConstructor
public class PurchaseController extends ExceptionHandling {

    private final PurchaseService purchaseService;
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/buy")
    @PreAuthorize("hasAnyAuthority('user:buy')")
    public ResponseEntity<HttpResponse> buy(@RequestBody PurchaseDTO purchaseDto) throws NotEnoughMoneyException, UserNotFoundException {
        purchaseService.buy(purchaseDto);
        return HttpAnswer.response(OK, GOODS_SUCCESSFULLY_PURCHASED);
    }

    @PostMapping("/get/{username}")
    public ResponseEntity<Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>>> get(@PathVariable String username,
                                                                                        HttpServletRequest request) throws NoRightException {
        checkUsernameForValidity(request, jwtTokenProvider, username);
        return new ResponseEntity<>(purchaseService.get(username), OK);
    }

    @PostMapping("/return/{username}")
    public ResponseEntity<Map<Boolean, Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>>>> returnTheProduct(@PathVariable String username,
                                                                                                                   @RequestBody Set<String> productNumbers,
                                                                                                                   HttpServletRequest request) throws NoRightException {
        checkUsernameForValidity(request, jwtTokenProvider, username);
        return new ResponseEntity<>(purchaseService.returnTheProduct(productNumbers, username), OK);
    }
}
