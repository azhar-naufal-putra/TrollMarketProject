package com.TrollMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Accounts")
public class Account {
    @Id
    @Column(name = "Username", length = 50)
    private String username;
    @Column(name = "Password", length = 255, nullable = false)
    private String password;
    @Column(name = "Role", length = 50, nullable = false)
    private String role;
    @Column(name="Name", length = 100)
    private String name;
    @Column(name = "Address", length = 255)
    private String address;
    @Column(name = "Balance")
    private BigDecimal balance;
    @OneToMany(mappedBy = "seller")
    private Set<Merchandise> merchandises;
    @OneToMany(mappedBy = "buyer")
    private Set<Order> buyerOrders;
    @OneToMany(mappedBy = "seller")
    private Set<OrderDetail> OrderDetails;
}
