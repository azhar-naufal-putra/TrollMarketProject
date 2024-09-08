package com.TrollMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Integer orderId;
    @Column(name = "OrderDate")
    private LocalDate orderDate;
    @Column(name = "BuyerUsername", nullable = false, length = 50)
    private String buyerUsername;
    @ManyToOne
    @JoinColumn(name = "BuyerUsername", insertable = false, updatable = false)
    private Account buyer;
    @Column(name = "Status", nullable = false, length = 20)
    private String status;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
}
