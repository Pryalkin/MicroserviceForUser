package com.shop.user.service.impl;

import com.shop.user.dto.*;
import com.shop.user.exception.model.NotFoundOrganizationException;
import com.shop.user.exception.model.ProductDoesNotExistException;
import com.shop.user.model.Purchase;
import com.shop.user.model.product.*;
import com.shop.user.model.user.Organization;
import com.shop.user.model.user.User;
import com.shop.user.repository.*;
import com.shop.user.service.ProductService;
import com.shop.user.service.SerialService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.shop.user.constant.ExceptionConstant.NO_SUCH_ORGANIZATION_EXISTS;
import static com.shop.user.constant.ExceptionConstant.PRODUCT_DOEST_NOT_EXIST;
import static com.shop.user.constant.HttpAnswer.*;
import static com.shop.user.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;
import static java.lang.Boolean.TRUE;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final OrganizationRepo organizationRepo;
    private final EvaluationRepo evaluationRepo;
    private final ReviewRepo reviewRepo;
    private final SerialService serialService;
    private final SerialRepo serialRepo;
    private final UserRepo userRepo;
    private final PurchaseRepo purchaseRepo;

    @Transactional
    @Override
    public void registerAProduct(ProductDTO productDto) throws NotFoundOrganizationException {
        Product product = new Product();
        String productId = generateId();
        while (productRepo.findByProdId(productId).isPresent())
            productId = generateId();
        product.setProdId(productId);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setRegistered(false);
        product.addAllCharacteristics(productDto.getCharacteristics());
        product.addAllKeywords(productDto.getKeywords());
        product.addAllSerials(serialService.put(productDto.getAmount()));
        product = productRepo.save(product);
        Organization org = organizationRepo.findActiveOrganization(productDto.getOrganization())
                .orElseThrow(() -> new NotFoundOrganizationException(NO_SUCH_ORGANIZATION_EXISTS));
        org.addProduct(product);
        organizationRepo.save(org);
    }

    @Override
    public Set<Product> getAllRegisteredProducts() {
        return productRepo.findByRegistered(TRUE).orElse(new HashSet<>());
    }

    @Override
    public String makeEvaluationAndReview(ReviewAndEvaluationDTO reviewAndEvaluationDTO) throws ProductDoesNotExistException {
        String message = "";
        Product product = productRepo.findByProdId(reviewAndEvaluationDTO.getProductId())
                .orElseThrow(() -> new ProductDoesNotExistException(PRODUCT_DOEST_NOT_EXIST));
        User user = product.getOrganization().getUser();
        if (evaluationRepo.findByProductOrganizationUser(user).isPresent()){
            message += EVALUATION_THE_PRODUCT;
        } else {
            Evaluation evaluation = new Evaluation();
            evaluation.setValue(reviewAndEvaluationDTO.getEvaluation());
            product.addEvaluation(evaluation);
            productRepo.save(product);
            message += PRODUCT_RATED;
        }
        if (reviewRepo.findByProductOrganizationUser(user).isPresent()){
            message += YOU_HAVE_ALREADY_LEFT_A_REVIEW;
        } else {
            Review review = new Review();
            review.setReview(reviewAndEvaluationDTO.getReview());
            product.addReview(review);
            productRepo.save(product);
            message += PRODUCT_REVIEW;
        }
        return message;
    }


    private String generateId() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
