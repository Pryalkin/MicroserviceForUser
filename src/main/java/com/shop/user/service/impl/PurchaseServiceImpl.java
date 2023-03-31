package com.shop.user.service.impl;

import com.shop.user.dto.*;
import com.shop.user.exception.model.NotEnoughMoneyException;
import com.shop.user.model.Purchase;
import com.shop.user.model.product.Product;
import com.shop.user.model.product.Serial;
import com.shop.user.model.user.User;
import com.shop.user.repository.*;
import com.shop.user.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Set;

import static com.shop.user.constant.ExceptionConstant.*;
import static com.shop.user.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;
import static java.lang.Boolean.TRUE;

@Service
@AllArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepo purchaseRepo;
    private final UserRepo userRepo;
    private final SerialRepo serialRepo;

    @Override
    @Transactional
    public void buy(PurchaseDTO purchaseDto) throws NotEnoughMoneyException, UsernameNotFoundException {
        Map<Product, Set<Serial>> products = purchaseDto.getProducts().keySet().stream()
                .flatMap(id -> serialRepo.findByProductProdId(id).stream().limit(purchaseDto.getProducts().get(id)))
                .filter(serial -> serial.getPurchase() == null)
                .collect(Collectors.groupingBy(Serial::getProduct, Collectors.toSet()));
        User user = deductMoneyFromTheBuyer(products, purchaseDto);
        sendMoneyToTheManufacturer(products);
        makePurchase(products, user);
    }

    @Override
    public Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>> get(String username) {
        return purchaseRepo.findByUserUsername(username).orElse(new HashSet<>())
                .stream().flatMap(purchase ->
                    purchase.getSerials()
                            .stream().map(this::createPurchasedSerialProductDTO).collect(Collectors.toSet()).stream()
                ).collect(Collectors.groupingBy(PurchasedSerialProductDTO::getPurchasedProductDTO, Collectors.toSet()));
    }

    @Override
    @Transactional
    public Map<Boolean, Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>>> returnTheProduct(Set<String> productNumbers, String username) {
        Map<Boolean, Set<Serial>> wishListOfReturnedProducts = productNumbers.stream().map(productNumber ->
                        serialRepo.findByProductNumber(productNumber).get())
                .collect(Collectors.partitioningBy(serial ->
                        serial.getPurchase().getDateOfPurchase().plusDays(1).isAfter(LocalDateTime.now()), Collectors.toSet()));

//        Set<Serial> wishListOfReturnedProducts = productNumbers.stream().map(productNumber ->
//                        serialRepo.findByProductNumber(productNumber).get())
//                .collect(Collectors.toSet());
//        Serial serial = serialRepo.findByProductNumber("3K65mzn7OV").get();



        if(!wishListOfReturnedProducts.get(TRUE).isEmpty()){
            Map<Boolean, Map<PurchasedProductDTO, Set<PurchasedSerialProductDTO>>> answer = wishListOfReturnedProducts.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> createPurchasedSerialProductDTOs(entry).stream()
                                    .collect(Collectors.groupingBy
                                            (PurchasedSerialProductDTO::getPurchasedProductDTO, Collectors.toSet()))));
            returnMoneyToTheUser(wishListOfReturnedProducts, username);
            takeMoneyFromTheCompany(wishListOfReturnedProducts);
            editPurchase(wishListOfReturnedProducts);
            return answer;
        } else return Collections.emptyMap();
//        return Collections.emptyMap();
    }

    private PurchasedSerialProductDTO createPurchasedSerialProductDTO(Serial serial) {
        PurchasedSerialProductDTO purchasedSerialProductDTO = new PurchasedSerialProductDTO();
        purchasedSerialProductDTO.setDateOfPurchase(serial.getPurchase().getDateOfPurchase());
        purchasedSerialProductDTO.setProductNumber(serial.getProductNumber());
        PurchasedProductDTO purchasedProductDTO = new PurchasedProductDTO();
        Product product = serial.getProduct();
        purchasedProductDTO.setProdId(product.getProdId());
        purchasedProductDTO.setName(product.getName());
        purchasedProductDTO.setDescription(product.getDescription());
        purchasedProductDTO.setPrice(product.getPrice());
        purchasedProductDTO.setOrganization(product.getOrganization().getName());
        purchasedProductDTO.getCharacteristicDtos().addAll(product.getCharacteristics()
                .stream().map(characteristic -> {
                    CharacteristicDTO characteristicDto = new CharacteristicDTO();
                    characteristicDto.setCharacteristic(characteristic.getCharacteristic());
                    characteristicDto.setDescription(characteristic.getDescription());
                    return characteristicDto;
                }).collect(Collectors.toSet()));
        purchasedSerialProductDTO.setPurchasedProductDTO(purchasedProductDTO);
        return purchasedSerialProductDTO;
    }



    private void editPurchase(Map<Boolean, Set<Serial>> wishListOfReturnedProducts) {
        Map<Purchase, Set<Serial>> map = wishListOfReturnedProducts.get(TRUE)
                .stream().collect(Collectors.groupingBy(Serial::getPurchase, Collectors.toSet()));
        Set<Purchase> purchases = map.entrySet().stream().map(e -> {
            Purchase purchase = e.getKey();
            purchase.removeAllSerial(e.getValue());
            return purchase;
        }).collect(Collectors.toSet());
        purchaseRepo.saveAll(purchases);
    }

    private void takeMoneyFromTheCompany(Map<Boolean, Set<Serial>> wishListOfReturnedProducts) {
        wishListOfReturnedProducts.get(TRUE).forEach(serial -> {
            User user = serial.getProduct().getOrganization().getUser();
            double summa = 0.0;
            if(serial.getProduct().getDiscount() == null || serial.getProduct().getDiscount().getBefore().plusDays(1).isBefore(LocalDate.now()))
                summa = serial.getProduct().getPrice();
            else summa = (serial.getProduct().getPrice() - (serial.getProduct().getPrice() * serial.getProduct().getDiscount().getDiscount() / 100));
            user.setBalance(user.getBalance() - summa);
            userRepo.save(user);
        });
    }

    private void returnMoneyToTheUser(Map<Boolean, Set<Serial>> wishListOfReturnedProducts, String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username));
        double returnedSummaForUser = wishListOfReturnedProducts.get(TRUE).stream().mapToDouble(serial -> {
            if(serial.getProduct().getDiscount() == null || serial.getProduct().getDiscount().getBefore().plusDays(1).isBefore(LocalDate.now()))
                return serial.getProduct().getPrice();
            else return (serial.getProduct().getPrice() - (serial.getProduct().getPrice() * serial.getProduct().getDiscount().getDiscount() / 100));
        }).sum();
        user.setBalance(user.getBalance() + returnedSummaForUser);
        userRepo.save(user);
    }

    private Set<PurchasedSerialProductDTO> createPurchasedSerialProductDTOs(Map.Entry<Boolean, Set<Serial>> map) {
        Set<PurchasedSerialProductDTO> purchasedSerialProductDTOs = map.getValue().stream()
                .map(this::createPurchasedSerialProductDTO).collect(Collectors.toSet());
        return purchasedSerialProductDTOs;
    }

    private void makePurchase(Map<Product, Set<Serial>> products, User user) {
        Purchase purchase = new Purchase();
        String purchaseNumber = generatePurchaseNumber();
        while (purchaseRepo.findByPurchaseNumber(purchaseNumber).isPresent())
            purchaseNumber = generatePurchaseNumber();
        purchase.setPurchaseNumber(purchaseNumber);
        products.forEach((key, value) -> purchase.addAllSerial(value));
        purchase.setDateOfPurchase(LocalDateTime.now());
        purchaseRepo.save(purchase);
        user.addPurchase(purchase);
        userRepo.save(user);
    }

    private void sendMoneyToTheManufacturer(Map<Product, Set<Serial>> products) {
        products.keySet().forEach(product -> {
            User u = product.getOrganization().getUser();
            double sum = 0.0;
            if(product.getDiscount() == null || product.getDiscount().getBefore().isBefore(LocalDate.now()))
                sum = product.getPrice() * products.get(product).size();
            else
                sum = (product.getPrice() - (product.getPrice() * product.getDiscount().getDiscount() / 100))
                        *  products.get(product).size();
            u.setBalance(u.getBalance() + sum);
            userRepo.save(u);
        });
    }

    private User deductMoneyFromTheBuyer(Map<Product, Set<Serial>> products, PurchaseDTO purchaseDto) throws NotEnoughMoneyException {
        double summa = products.keySet().stream().mapToDouble(p -> {
            if(p.getDiscount() == null || p.getDiscount().getBefore().isBefore(LocalDate.now()))
                return p.getPrice() * products.get(p).size();
            else return (p.getPrice() - (p.getPrice() * p.getDiscount().getDiscount() / 100)) *  products.get(p).size();
        }).sum();
        User user = userRepo.findByUsername(purchaseDto.getBuyer())
                .orElseThrow(() -> new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME));
        double balance = user.getBalance();
        if (balance < summa) throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY);
        user.setBalance(balance - summa);
        return user;
    }

    private String generatePurchaseNumber() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
