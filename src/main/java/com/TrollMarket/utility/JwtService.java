package com.TrollMarket.utility;

import com.TrollMarket.entity.Account;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(Account account);
    Boolean isValid(String token, UserDetails userDetails);
    Claims getClaims(String token);
}
