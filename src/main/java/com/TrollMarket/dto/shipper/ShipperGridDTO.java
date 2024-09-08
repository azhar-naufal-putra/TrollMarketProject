package com.TrollMarket.dto.shipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShipperGridDTO {
    private Integer shipperId;
    private String shipperName;
    private String price;
    private String service;
    private Boolean editable;
}
