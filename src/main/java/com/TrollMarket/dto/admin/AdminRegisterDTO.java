package com.TrollMarket.dto.admin;

import com.TrollMarket.validation.InputMatch;
import com.TrollMarket.validation.account.UniqueAccountUsername;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@InputMatch(first = "password", second = "passwordConfirmation", message = "Password does not match!!")
public class AdminRegisterDTO {
    @NotBlank(message = "Username cannot be blank!!")
    @Length(max = 50, message = "Username cannot be more than 50 characters!")
    @UniqueAccountUsername(message = "Username is already exist!!")
    private String username;
    @NotBlank(message = "Password cannot be blank!!")
    private String password;
    private String passwordConfirmation;
}
