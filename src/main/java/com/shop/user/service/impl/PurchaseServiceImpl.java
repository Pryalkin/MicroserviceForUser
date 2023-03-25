package com.shop.user.service.impl;

import com.shop.user.dto.PurchaseDto;
import com.shop.user.exception.model.NotEnoughMoneyException;
import com.shop.user.exception.model.ProductDoesNotExistException;
import com.shop.user.exception.model.UserNotFoundException;
import com.shop.user.model.Purchase;
import com.shop.user.model.product.Product;
import com.shop.user.model.user.Organization;
import com.shop.user.model.user.User;
import com.shop.user.repository.OrganizationRepo;
import com.shop.user.repository.ProductRepo;
import com.shop.user.repository.PurchaseRepo;
import com.shop.user.repository.UserRepo;
import com.shop.user.service.PurchaseService;
import com.sun.source.tree.TryTree;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.shop.user.constant.ExceptionConstant.*;
import static com.shop.user.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;

@Service
@AllArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepo purchaseRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final OrganizationRepo organizationRepo;

    @Override
    @Transactional
    public void buy(PurchaseDto productDto) throws NotEnoughMoneyException, UserNotFoundException {
        Set<Product> products = productDto.getProductsId().stream().map(id ->
                productRepo.findByProductId(id).get()).collect(Collectors.toSet());
        User user = userRepo.findByUsername(productDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + productDto.getUsername()));
        double summa = products.stream().mapToDouble(Product::getPrice).sum();
        double balance = user.getBalance();
        if (summa > balance) throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY);
        user.setBalance(balance - summa);
        user = userRepo.save(user);
        Purchase purchase = new Purchase();
        purchase.setProducts(products);
        purchase.setUser(user);
        purchase.setDateOfPurchase(LocalDateTime.now());
        purchaseRepo.save(purchase);
        Set<Organization> organizations = new HashSet<>();
        products.forEach(product -> {
            Organization org = organizationRepo.findByProduct(product.getId()).get();
            organizations.add(org);
        });
        Map<Organization, List<Product>> map = organizations.stream()
                .collect(Collectors.groupingBy(Product::getOrganization));
        map.keySet().parallelStream().forEach(key -> {
            User u = key.getUser();
            double bal = u.getBalance();
            double sum = map.get(key).stream().mapToDouble(Product::getPrice).sum();
            u.setBalance(bal + sum);
            userRepo.save(u);
        });
    }

    @Override
    public Set<Purchase> get(String username) {
        return purchaseRepo.findByUserUsername(username).orElse(new HashSet<>());
    }
}
