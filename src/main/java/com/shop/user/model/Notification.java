package com.shop.user.model;

import com.shop.user.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    @Column(name = "date_of_creation")
    private LocalDateTime dateOfCreation;
    private String notification;
    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User user;
}
