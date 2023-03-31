package com.shop.user.model.user;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shop.user.json.serializer.NotificationSetSerializer;
import com.shop.user.json.serializer.PurchaseSetSerializer;
import com.shop.user.model.Notification;
import com.shop.user.model.Purchase;
import com.shop.user.json.serializer.OrganizationSetSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @NotNull
    @EqualsAndHashCode.Include
    private String username;
    @Email
    @NotNull
    @EqualsAndHashCode.Include
    private String email;
    @EqualsAndHashCode.Include
    private String password;
    private Double balance;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonSerialize(using = OrganizationSetSerializer.class)
    private Set<Organization> organizations = new HashSet<>();
    @JsonSerialize(using = NotificationSetSerializer.class)
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Notification> notifications = new HashSet<>();
    private String role;
    private String[] authorities;
    private String activity;
    @JsonSerialize(using = PurchaseSetSerializer.class)
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Purchase> purchases = new HashSet<>();

    public void addOrganization(Organization organization) {
        organizations.add(organization);
        organization.setUser(this);
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        purchase.setUser(this);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
        notification.setUser(this);
    }
}
