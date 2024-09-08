package com.TrollMarket.validation.shipper;

import com.TrollMarket.service.abstraction.ShipperService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueShipperNameValidation implements ConstraintValidator<UniqueShipperName, Object> {
    private final ShipperService service;
    public String shipperId;
    public String shipperName;

    @Autowired
    public UniqueShipperNameValidation(ShipperService service){
        this.service = service;
    }

    @Override
    public void initialize(UniqueShipperName constraintAnnotation) {
        this.shipperId = constraintAnnotation.shipperId();
        this.shipperName = constraintAnnotation.shipperName();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        return !service.checkExistingShipperName(
                (Integer) new BeanWrapperImpl(object).getPropertyValue(this.shipperId),
                (String) new BeanWrapperImpl(object).getPropertyValue(this.shipperName)
        );
    }
}
