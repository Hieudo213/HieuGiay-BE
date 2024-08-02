package com.example.hieugiaybe.entities;

import com.example.hieugiaybe.entities.enums.PaymentStatus;
import com.example.hieugiaybe.entities.enums.ShippingStatus;
import com.example.hieugiaybe.security.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Integer totalOrderMoney;

    private Integer shippingFee;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Product> products = new ArrayList<>();
}
