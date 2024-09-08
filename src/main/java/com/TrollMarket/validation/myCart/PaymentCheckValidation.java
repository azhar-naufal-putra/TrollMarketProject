package com.TrollMarket.validation.myCart;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.math.BigDecimal;

public class PaymentCheckValidation implements ConstraintValidator<PaymentCheck, Object>{
    private String balance;
    private String totalPayment;

    @Override
    public void initialize(PaymentCheck constraintAnnotation) {
        this.balance = constraintAnnotation.balance();
        this.totalPayment = constraintAnnotation.totalPayment();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try{
            var balanceValue = new BeanWrapperImpl(object).getPropertyValue(this.balance);
            var totalPaymentValue = new BeanWrapperImpl(object).getPropertyValue(this.totalPayment);

            BigDecimal balance = new BigDecimal(balanceValue.toString());
            BigDecimal totalPayment = new BigDecimal(totalPaymentValue.toString());

            return balance.compareTo(totalPayment) >= 0;
        }catch(Exception ex){
            return false;
        }
    }
}
