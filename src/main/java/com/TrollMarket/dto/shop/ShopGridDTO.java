package com.TrollMarket.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShopGridDTO {
    private Integer merchandiseId;
    private String merchandiseName;
    private String price;
}
