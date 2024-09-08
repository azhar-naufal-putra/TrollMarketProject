package com.TrollMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Shippers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShipperId")
    private Integer shipperId;
    @Column(name = "Name", nullable = false, length = 100)
    private String shipperName;
    @Column(name = "Price", nullable = false)
    private BigDecimal price;
    @Column(name = "Service", nullable = false)
    private Boolean service;
    @OneToMany(mappedBy = "shipper")
    private List<OrderDetail> orderDetails;
}
