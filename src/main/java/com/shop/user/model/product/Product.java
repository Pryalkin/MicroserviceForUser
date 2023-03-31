package com.shop.user.model.product;

import com.shop.user.model.user.Organization;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    @EqualsAndHashCode.Include
    private String prodId;
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;
    private Double price;
    private Boolean registered;
    @ManyToOne(fetch = FetchType.LAZY)
    private Discounts discount;
    @NotNull
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Keyword> keywords = new HashSet<>();
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Review> reviews = new HashSet<>();
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Evaluation> evaluations = new HashSet<>();
    private Boolean isActive;
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Characteristic> characteristics = new HashSet<>();
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Serial> serials = new HashSet<>();

    public void addKeyword(Keyword keyword) {
        keywords.add(keyword);
        keyword.setProduct(this);
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
    }

    public void addEvaluation(Evaluation evaluation) {
        evaluations.add(evaluation);
        evaluation.setProduct(this);
    }

    public void addCharacteristics(Characteristic characteristic) {
        characteristics.add(characteristic);
        characteristic.setProduct(this);
    }

    public void addSerial(Serial serial) {
        serials.add(serial);
        serial.setProduct(this);
    }


    public void addAllCharacteristics(Set<Characteristic> characteristicAll) {
        characteristics.addAll(characteristicAll);
        characteristicAll.forEach(s -> s.setProduct(this));
    }

    public void addAllKeywords(Set<Keyword> keywordAll) {
        keywords.addAll(keywordAll);
        keywordAll.forEach(s -> s.setProduct(this));
    }

    public void addAllSerials(Set<Serial> serialAll) {
        serials.addAll(serialAll);
        serialAll.forEach(s -> s.setProduct(this));
    }
}
