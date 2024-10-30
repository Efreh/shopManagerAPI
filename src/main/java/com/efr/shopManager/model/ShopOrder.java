package com.efr.shopManager.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"user", "products"})
@Entity
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalPrise;
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ShopUser user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "shop_order_products",
            joinColumns = @JoinColumn(name = "shop_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    public ShopOrder(ShopUser user, OrderStatus status) {
        this.status = status;
        this.user = user;
    }
}