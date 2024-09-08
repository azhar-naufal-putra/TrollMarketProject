package com.TrollMarket.service.implementation;

import com.TrollMarket.dto.account.AccountLoginDTO;
import com.TrollMarket.dto.account.AccountRegisterDTO;
import com.TrollMarket.dto.account.AccountTokenDTO;
import com.TrollMarket.entity.Account;
import com.TrollMarket.repository.AccountRepository;
import com.TrollMarket.service.abstraction.AccountService;
import com.TrollMarket.utility.JwtService;
import com.TrollMarket.utility.UserDetailsImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImplementation implements AccountService, UserDetailsService {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AccountServiceImplementation(
            AccountRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void register(AccountRegisterDTO accountRegisterDTO){
        String hashPassword = passwordEncoder.encode(accountRegisterDTO.getPassword());
        Account account = new Account();
        account.setUsername(accountRegisterDTO.getUsername());
        account.setPassword(hashPassword);
        account.setAddress(accountRegisterDTO.getAddress().trim().equals("") ? null : accountRegisterDTO.getAddress());
        account.setBalance(new BigDecimal(0));
        account.setName(accountRegisterDTO.getName());
        account.setRole(accountRegisterDTO.getRole());
        repository.save(account);
    };

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findById(username).orElseThrow();
        return new UserDetailsImplementation(account);
    }


    @Override
    public AccountTokenDTO createToken(AccountLoginDTO accountLoginDTO){
        Account account = new Account();
        try{
            account =repository.findById(accountLoginDTO.getUsername()).orElseThrow();
        }catch (Exception ex){
            throw new IllegalArgumentException("Wrong username, role, and password!");
        }
        if(
            !passwordEncoder.matches(accountLoginDTO.getPassword(), account.getPassword())
            || !accountLoginDTO.getRole().equals(account.getRole())
        ){
            throw new IllegalArgumentException("Wrong username, role, and password!");
        };
        String token = jwtService.generateToken(account);
        return new AccountTokenDTO(token);
    }

    @Override
    public Boolean checkExistingUsername(String username){
        return repository.existsById(username);
    };


}
