package com.shop.user.controller;

import com.shop.user.constant.HttpAnswer;
import com.shop.user.dto.ProductDto;
import com.shop.user.dto.ProductDto2;
import com.shop.user.exception.ExceptionHandling;
import com.shop.user.exception.model.NotFoundOrganizationException;
import com.shop.user.exception.model.ProductDoesNotExistException;
import com.shop.user.model.HttpResponse;
import com.shop.user.model.product.Product;
import com.shop.user.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.shop.user.constant.HttpAnswer.APPLICATION_SENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user/product")
@AllArgsConstructor
public class ProductController extends ExceptionHandling {

    private final ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('user:create_product')")
    public ResponseEntity<HttpResponse> create(@RequestBody ProductDto productDto) throws NotFoundOrganizationException {
        productService.registerAProduct(productDto);
        return HttpAnswer.response(OK, APPLICATION_SENT);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Set<Product>> getAll(){
        return new ResponseEntity<>(productService.getAllRegisteredProducts(), OK);
    }

    @PostMapping("/evaluationAndReview")
    public ResponseEntity<HttpResponse> makeEvaluationAndReview(@RequestBody ProductDto2 productDto2) throws ProductDoesNotExistException {
        String message = productService.makeEvaluationAndReview(productDto2);
        return HttpAnswer.response(OK, message);
    }


}
