package com.TrollMarket.dto.account;

import com.TrollMarket.validation.InputMatch;
import com.TrollMarket.validation.account.UniqueAccountUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@InputMatch(first = "password", second = "passwordConfirmation", message = "Password doesn't match!!!")
public class AccountRegisterDTO {
    @NotBlank(message = "Username cannot be blank!!")
    @UniqueAccountUsername(message = "Username is already exist!!")
    @Length(max = 50, message = "Username cannot be more than 50 characters")
    private String username;
    @NotBlank(message = "Password cannot be blank!!")
    private String password;
    private String passwordConfirmation;
    @NotBlank(message = "Name cannot be blank!!")
    @Length(max = 100, message = "Name cannot be more than 100 characters")
    private String name;
    @Length(max = 255, message = "Address cannot be more than 255 characters")
    private String address;
    private String role;
}
