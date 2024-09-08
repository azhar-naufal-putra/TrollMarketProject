package com.TrollMarket.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopMerchandiseInfoDTO {
    private String merchandiseName;
    private String categoryName;
    private String description;
    private String price;
    private String sellerName;
}
