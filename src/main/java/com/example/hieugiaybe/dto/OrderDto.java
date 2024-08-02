package com.example.hieugiaybe.dto;

import com.example.hieugiaybe.entities.Product;
import com.example.hieugiaybe.entities.enums.PaymentStatus;
import com.example.hieugiaybe.entities.enums.ShippingStatus;
import com.example.hieugiaybe.security.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Integer id;

    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Integer totalOrderMoney;

    private Integer shippingFee;

    private String email;

    private List<Product> products;

    private User user;

}
