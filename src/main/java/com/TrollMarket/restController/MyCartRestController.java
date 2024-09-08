package com.TrollMarket.restController;

import com.TrollMarket.dto.myCart.MyCartPurchaseDTO;
import com.TrollMarket.service.abstraction.MyCartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/my-cart")
public class MyCartRestController {
    private MyCartService service;

    @Autowired
    public MyCartRestController(MyCartService service){
        this.service = service;
    }

    @GetMapping("payment-info")
    public ResponseEntity<Object> getInfoPayment(
            @RequestParam Integer orderId,
            @RequestParam String username
    ){
        try{
            MyCartPurchaseDTO myCartPurchaseDTO = service.getMyCartPaymentInfo(orderId, username);
            return ResponseEntity.status(HttpStatus.OK).body(myCartPurchaseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("purchase")
    public ResponseEntity<Object> purchase(
            @Valid @RequestBody MyCartPurchaseDTO myCartPurchaseDTO,
            BindingResult bindingResult
    ){
        try{
            if (bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getAllErrors());
            }else{
                service.purchaseOrder(myCartPurchaseDTO);
                return ResponseEntity.status(HttpStatus.OK).body("Purchase Success");
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
