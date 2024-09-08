package com.TrollMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Merchandises")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Merchandise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MerchandiseId")
    private Integer merchandiseId;
    @Column(name = "MerchandiseName", length = 100, nullable = false)
    private String merchandiseName;
    @Column(name = "Description", length = 255)
    private String description;
    @Column(name = "Price", nullable = false)
    private BigDecimal price;
    @Column(name = "Discontinue", nullable = false)
    private Boolean discontinue;
    @Column(name = "CategoryId", nullable = false)
    private Integer categoryId;
    @ManyToOne
    @JoinColumn(name = "CategoryId", insertable = false, updatable = false)
    private Category category;
    @OneToMany(mappedBy = "merchandise")
    private List<OrderDetail> orderDetails;
    @Column(name = "SellerUsername", nullable = false, length = 50)
    private String sellerUsername;
    @ManyToOne
    @JoinColumn(name = "SellerUsername", insertable = false, updatable = false)
    private Account seller;

}
