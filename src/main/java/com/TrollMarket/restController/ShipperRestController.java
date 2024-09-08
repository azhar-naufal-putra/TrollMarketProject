package com.TrollMarket.restController;

import com.TrollMarket.dto.shipper.ShipperInsertUpdateDTO;
import com.TrollMarket.service.abstraction.ShipperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shipper")
public class ShipperRestController {
    private final ShipperService service;

    @Autowired
    public ShipperRestController(ShipperService service){
        this.service = service;
    }

    @GetMapping("shipper-form/{shipperId}")
    public ResponseEntity<Object> insertUpdate(
            @PathVariable Integer shipperId
    ){
        try{
            ShipperInsertUpdateDTO shipperInsertUpdateDTO = service.getShipper(shipperId);
            return ResponseEntity.status(HttpStatus.OK).body(shipperInsertUpdateDTO);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("shipper-form")
    public ResponseEntity<Object> insertUpdate(
            @Valid @RequestBody ShipperInsertUpdateDTO shipperInsertUpdateDTO,
            BindingResult bindingResult
    ){
        try{
            if(bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getAllErrors());
            }else{
                service.save(shipperInsertUpdateDTO);
                return ResponseEntity.status(HttpStatus.OK).body("Success");
            }
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
