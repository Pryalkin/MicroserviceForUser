package com.shop.user.model;

import com.shop.user.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @EqualsAndHashCode.Include
    private String header;
    @Column(name = "date_of_creation")
    @EqualsAndHashCode.Include
    private LocalDateTime dateOfCreation;
    @EqualsAndHashCode.Include
    private String notification;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
