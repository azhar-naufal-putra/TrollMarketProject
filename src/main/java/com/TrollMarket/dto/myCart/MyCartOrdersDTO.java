package com.TrollMarket.dto.myCart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyCartOrdersDTO {
    private Integer merchandiseId;
    private String merchandiseName;
    private Integer quantity;
    private String shipperName;
    private String sellerName;
    private String totalPrice;
}
