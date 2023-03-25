package com.shop.user.model.product;

import com.shop.user.model.Notification;
import com.shop.user.model.Purchase;
import com.shop.user.model.user.Organization;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String productId;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name="organization_id", insertable = false, updatable = false)
    private Organization organization;
    private Double price;
    private Boolean registered;
    @ManyToOne
    @JoinColumn(name="notification_id", insertable = false, updatable = false)
    private Notification notification;
    @NotNull
    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Keyword> keywords;
    @NotNull
    private Long amount;
    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Review> review;
    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Evaluation> evaluations;
    private Boolean isActive;
    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Characteristics> characteristics;
    @ManyToOne
    @JoinColumn(name="purchase_id", insertable = false, updatable = false)
    private Purchase purchase;

}
