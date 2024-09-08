package com.TrollMarket.dto.myCart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyCartGridDTO {
    private Integer orderId;
    private List<MyCartOrdersDTO> orders;
}
