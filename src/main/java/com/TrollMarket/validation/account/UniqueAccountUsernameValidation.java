package com.TrollMarket.validation.account;

import com.TrollMarket.service.abstraction.AccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueAccountUsernameValidation implements ConstraintValidator<UniqueAccountUsername, String> {
    private final AccountService service;

    @Autowired
    public UniqueAccountUsernameValidation(AccountService service){
        this.service = service;
    }


    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !service.checkExistingUsername(username);
    }
}
