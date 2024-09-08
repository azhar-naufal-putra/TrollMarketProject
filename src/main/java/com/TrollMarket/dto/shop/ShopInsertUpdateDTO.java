package com.TrollMarket.dto.shop;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopInsertUpdateDTO {
    private Integer merchandiseId;
    @NotNull(message = "Quantity must be specified")
    @Min(value = 1, message = "Quantity of product must be equals or greater than 1")
    private Integer quantity;
    @NotNull(message = "Shipment must be specified")
    private Integer shipperId;
    private String buyerUsername;
}
