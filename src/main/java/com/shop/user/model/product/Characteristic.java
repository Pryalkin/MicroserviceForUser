package com.shop.user.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Characteristic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @EqualsAndHashCode.Include
    private String characteristic;
    @NotNull
    @EqualsAndHashCode.Include
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
