package com.shop.user.model.user;

import com.shop.user.model.Notification;
import com.shop.user.model.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String username;
    @Email
    @NotNull
    private String email;
    private String password;
    private Double balance;
    @OneToMany
    @JoinColumn(name="user_id")
    private Set<Organization> organization;
    @OneToMany
    @JoinColumn(name="user_id")
    private Set<Notification> notifications;
    private String role;
    private String[] authorities;
    private String activity;
    @OneToMany
    @JoinColumn(name="user_id")
    private Set<Purchase> purchases;

}
