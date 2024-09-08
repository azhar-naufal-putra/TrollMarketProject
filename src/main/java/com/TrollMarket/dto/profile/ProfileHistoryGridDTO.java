package com.TrollMarket.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileHistoryGridDTO {
    private String orderDate;
    private String merchandiseName;
    private Integer quantity;
    private String shipperName;
    private String totalPrice;
}
