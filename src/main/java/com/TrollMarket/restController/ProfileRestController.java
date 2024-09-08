package com.TrollMarket.restController;

import com.TrollMarket.dto.profile.ProfileAddBalanceDTO;
import com.TrollMarket.service.abstraction.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
public class ProfileRestController {
    private final ProfileService service;

    @Autowired
    public ProfileRestController(ProfileService service){
        this.service = service;
    }

    @PutMapping("balance")
    public ResponseEntity<Object> updateBalance(
            @Valid @RequestBody ProfileAddBalanceDTO profileAddBalanceDTO,
            BindingResult bindingResult
    ){
        try{
            if(bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getAllErrors());
            }else{
                service.updateBalance(profileAddBalanceDTO);
                return ResponseEntity.status(HttpStatus.OK).body("Update Balance Success");
            }

        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
