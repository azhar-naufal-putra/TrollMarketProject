package com.TrollMarket.dto.shipper;

import com.TrollMarket.validation.shipper.UniqueShipperName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@UniqueShipperName(shipperId = "shipperId", shipperName = "shipperName", message = "Shipment is already exist!!!")
public class ShipperInsertUpdateDTO {
    private Integer shipperId;
    @NotBlank(message = "Shipper name cannot be blank!!")
    @Length(max = 100, message = "Shipper name length cannot be greater than 100 characters")
    private String shipperName;
    @Min(value = 0, message = "Price must be equals or greater than 0")
    @NotNull(message = "Price must be specified")
    private BigDecimal price;
    private Boolean service;
}
