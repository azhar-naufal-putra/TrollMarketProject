package com.TrollMarket.restController;

import com.TrollMarket.dto.account.AccountLoginDTO;
import com.TrollMarket.service.abstraction.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
public class AccountRestController {
    private final AccountService service;

    @Autowired
    public AccountRestController(AccountService service){
        this.service = service;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<Object> createToken(
            @RequestBody AccountLoginDTO accountLoginDTO
    ){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.createToken(accountLoginDTO));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
