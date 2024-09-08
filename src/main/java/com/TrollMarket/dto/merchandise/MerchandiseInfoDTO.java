package com.TrollMarket.dto.merchandise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseInfoDTO {
    private String merchandiseName;
    private String categoryName;
    private String description;
    private String price;
    private String discontinue;
    private String sellerName;
}
