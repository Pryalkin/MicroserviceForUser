package com.shop.user.model;

import com.shop.user.model.product.Product;
import com.shop.user.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @JoinColumn(name = "purchase_id")
    private Set<Product> products;
    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User user;
    @Column(name = "date_of_purchase")
    private LocalDateTime dateOfPurchase;
}
