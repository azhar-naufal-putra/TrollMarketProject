package com.TrollMarket.dto.myCart;

import com.TrollMarket.validation.myCart.PaymentCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@PaymentCheck(balance = "myBalance", totalPayment = "totalPayment", message = "You Broke! your balance is not enough to purchase all these products!!")
public class MyCartPurchaseDTO {
    private Integer orderId;
    private BigDecimal myBalance;
    private BigDecimal totalPayment;
    private String myBalanceParsed;
    private String totalPaymentParsed;
}
