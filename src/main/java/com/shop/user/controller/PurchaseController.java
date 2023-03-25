package com.shop.user.controller;

import com.shop.user.constant.HttpAnswer;
import com.shop.user.dto.ProductDto;
import com.shop.user.dto.PurchaseDto;
import com.shop.user.exception.ExceptionHandling;
import com.shop.user.exception.model.NotEnoughMoneyException;
import com.shop.user.exception.model.NotFoundOrganizationException;
import com.shop.user.exception.model.UserNotFoundException;
import com.shop.user.model.HttpResponse;
import com.shop.user.model.Purchase;
import com.shop.user.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.shop.user.constant.HttpAnswer.APPLICATION_SENT;
import static com.shop.user.constant.HttpAnswer.GOODS_SUCCESSFULLY_PURCHASED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user/purchase")
@AllArgsConstructor
public class PurchaseController extends ExceptionHandling {

    private final PurchaseService purchaseService;

    @PostMapping("/buy")
    @PreAuthorize("hasAnyAuthority('user:buy')")
    public ResponseEntity<HttpResponse> buy(@RequestBody PurchaseDto purchaseDto) throws NotEnoughMoneyException, UserNotFoundException {
        purchaseService.buy(purchaseDto);
        return HttpAnswer.response(OK, GOODS_SUCCESSFULLY_PURCHASED);
    }

    @PostMapping("/get/{username}")
    public ResponseEntity<Set<Purchase>> get(@PathVariable String username) {
        return new ResponseEntity<>(purchaseService.get(username), OK);
    }
}
