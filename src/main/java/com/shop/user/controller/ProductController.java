package com.shop.user.controller;

import com.shop.user.constant.HttpAnswer;
import com.shop.user.dto.ProductDTO;
import com.shop.user.dto.PurchasedProductDTO;
import com.shop.user.dto.PurchasedSerialProductDTO;
import com.shop.user.dto.ReviewAndEvaluationDTO;
import com.shop.user.exception.ExceptionHandling;
import com.shop.user.exception.model.NoRightException;
import com.shop.user.exception.model.NotFoundOrganizationException;
import com.shop.user.exception.model.ProductDoesNotExistException;
import com.shop.user.model.HttpResponse;
import com.shop.user.model.product.Product;
import com.shop.user.service.ProductService;
import com.shop.user.utility.JWTTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.shop.user.constant.HttpAnswer.APPLICATION_SENT;
import static com.shop.user.controller.security.ValidUsernameSecurity.checkUsernameForValidity;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user/product")
@AllArgsConstructor
public class ProductController extends ExceptionHandling {

    private final ProductService productService;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('user:create_product')")
    public ResponseEntity<HttpResponse> create(@RequestBody ProductDTO productDto) throws NotFoundOrganizationException {
        productService.registerAProduct(productDto);
        return HttpAnswer.response(OK, APPLICATION_SENT);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Set<Product>> getAll(){
        return new ResponseEntity<>(productService.getAllRegisteredProducts(), OK);
    }

    @PostMapping("/evaluationAndReview")
    public ResponseEntity<HttpResponse> makeEvaluationAndReview(@RequestBody ReviewAndEvaluationDTO reviewAndEvaluationDTO) throws ProductDoesNotExistException {
        String message = productService.makeEvaluationAndReview(reviewAndEvaluationDTO);
        return HttpAnswer.response(OK, message);
    }

}
