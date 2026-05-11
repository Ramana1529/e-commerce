package com.ecommerce.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @PrePersist
    public void prePersist() {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        if (this.totalPrice == null) {
            this.totalPrice = BigDecimal.ZERO;
        }
    }

    @PostLoad
    public void postLoad() {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        if (this.totalPrice == null) {
            this.totalPrice = BigDecimal.ZERO;
        }
    }
}
