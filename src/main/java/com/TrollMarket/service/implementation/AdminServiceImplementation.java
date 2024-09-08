package com.TrollMarket.service.implementation;

import com.TrollMarket.dto.admin.AdminRegisterDTO;
import com.TrollMarket.entity.Account;
import com.TrollMarket.repository.AccountRepository;
import com.TrollMarket.service.abstraction.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImplementation implements AdminService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImplementation(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerAdmin(AdminRegisterDTO adminRegisterDTO){
        String hashPassword = passwordEncoder.encode(adminRegisterDTO.getPassword());
        Account account = new Account();
        account.setUsername(adminRegisterDTO.getUsername());
        account.setRole("Admin");
        account.setPassword(hashPassword);
        accountRepository.save(account);
    };



}
