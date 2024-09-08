package com.TrollMarket.entity;

import com.TrollMarket.entity.composite.OrderDetailId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OrderDetails")
@Entity
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "OrderId", insertable = false, updatable = false)
    private Order order;
    @ManyToOne
    @MapsId("merchandiseId")
    @JoinColumn(name = "MerchandiseId", insertable = false, updatable = false)
    private Merchandise merchandise;
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
    @Column(name = "Price", nullable = false)
    private BigDecimal price;
    @Column(name = "ShipmentPrice", nullable = false)
    private BigDecimal shipmentPrice;
    @Column(name="ShipperId", nullable = false)
    private Integer shipperId;
    @ManyToOne
    @JoinColumn(name = "ShipperId", insertable = false, updatable = false)
    private Shipper shipper;
    @Column(name = "SellerUsername", length = 50, nullable = false)
    private String sellerUsername;
    @ManyToOne
    @JoinColumn(name = "SellerUsername", insertable = false, updatable = false)
    private Account seller;
}

