package com.TrollMarket.dto.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoryGridDTO {
    private String orderDate;
    private String sellerName;
    private String buyerName;
    private String merchandiseName;
    private Integer quantity;
    private String shipperName;
    private String totalPrice;
}
