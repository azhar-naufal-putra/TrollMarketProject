package com.TrollMarket.dto.profile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileAddBalanceDTO {
    private String username;
    @NotNull(message = "Balance must be specified")
    @Min(value = 10000, message = "Minimum to add balance is 10k rupiah")
    private BigDecimal balance;
}
