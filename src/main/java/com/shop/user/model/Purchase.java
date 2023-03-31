package com.shop.user.model;

import com.shop.user.model.product.Serial;
import com.shop.user.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String purchaseNumber;
    @OneToMany(
            mappedBy = "purchase",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Serial> serials = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(name = "date_of_purchase")
    @EqualsAndHashCode.Include
    private LocalDateTime dateOfPurchase;

    public void addStock(Serial serial) {
        serials.add(serial);
        serial.setPurchase(this);
    }

    public void addAllSerial(Set<Serial> serialAll) {
        serials.addAll(serialAll);
        serialAll.forEach(serial -> serial.setPurchase(this));
    }

    public void removeAllSerial(Set<Serial> serialAll) {
        serials.removeAll(serialAll);
        serialAll.forEach(serial -> serial.setPurchase(null));
    }
}
