package com.shop.user.service.impl;

import com.shop.user.dto.ProductDto;
import com.shop.user.dto.ProductDto2;
import com.shop.user.exception.model.NotFoundOrganizationException;
import com.shop.user.exception.model.ProductDoesNotExistException;
import com.shop.user.model.product.*;
import com.shop.user.model.user.Organization;
import com.shop.user.model.user.User;
import com.shop.user.repository.*;
import com.shop.user.service.ProductService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.shop.user.constant.ExceptionConstant.NO_SUCH_ORGANIZATION_EXISTS;
import static com.shop.user.constant.ExceptionConstant.PRODUCT_DOEST_NOT_EXIST;
import static com.shop.user.constant.HttpAnswer.*;
import static java.lang.Boolean.TRUE;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final KeywordRepo keywordRepo;
    private final OrganizationRepo organizationRepo;
    private final CharacteristicsRepo characteristicsRepo;
    private final EvaluationRepo evaluationRepo;
    private final ReviewRepo reviewRepo;

    @Transactional
    @Override
    public void registerAProduct(ProductDto productDto) throws NotFoundOrganizationException {
        Product product = new Product();
        String productId = generateId();
        while (productRepo.findByProductId(productId).isPresent())
            productId = generateId();
        product.setProductId(productId);
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        Organization org = organizationRepo.findActiveOrganization(productDto.getOrganization()).orElseThrow(() -> new NotFoundOrganizationException(NO_SUCH_ORGANIZATION_EXISTS));
        product.setOrganization(org);
        Set<Characteristics> characteristics = new HashSet<>(characteristicsRepo.saveAll(productDto.getCharacteristics()));
        product.setCharacteristics(characteristics);
        product.setPrice(productDto.getPrice());
        product.setRegistered(false);
        Set<Keyword> keywords = productDto.getKeywords()
                .stream().map(keywordRepo::save).collect(Collectors.toSet());
        product.setKeywords(keywords);
        product.setAmount(productDto.getAmount());
        product.setReview(new HashSet<>());
        product.setEvaluations(new HashSet<>());
        productRepo.save(product);
    }

    @Override
    public Set<Product> getAllRegisteredProducts() {
        return productRepo.findByRegistered(TRUE).orElse(new HashSet<>());
    }

    @Override
    public String makeEvaluationAndReview(ProductDto2 productDto2) throws ProductDoesNotExistException {
        String message = "";
        Product product = productRepo.findByProductId(productDto2.getProductId())
                .orElseThrow(() -> new ProductDoesNotExistException(PRODUCT_DOEST_NOT_EXIST));
        User user = product.getOrganization().getUser();
        if (evaluationRepo.findByProductOrganizationUser(user).isPresent()){
            message += EVALUATION_THE_PRODUCT;
        } else {
            Evaluation evaluation = new Evaluation();
            evaluation.setValue(productDto2.getEvaluation());
            evaluationRepo.save(evaluation);
            product.getEvaluations().add(evaluation);
            message += PRODUCT_RATED;
        }
        if (reviewRepo.findByProductOrganizationUser(user).isPresent()){
            message += YOU_HAVE_ALREADY_LEFT_A_REVIEW;
        } else {
            Review review = new Review();
            review.setReview(productDto2.getReview());
            reviewRepo.save(review);
            product.getReview().add(review);
            message += PRODUCT_REVIEW;
        }
        return message;
    }

    private String generateId() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
