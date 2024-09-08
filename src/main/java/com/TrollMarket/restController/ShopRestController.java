package com.TrollMarket.restController;

import com.TrollMarket.dto.ShipperOptionDTO;
import com.TrollMarket.dto.shop.ShopInsertUpdateDTO;
import com.TrollMarket.dto.shop.ShopMerchandiseInfoDTO;
import com.TrollMarket.service.abstraction.ShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/shop")
public class ShopRestController {
    private final ShopService service;

    @Autowired
    public ShopRestController(ShopService service){
        this.service = service;
    }

    @GetMapping("merchandise-info")
    public ResponseEntity<Object> getDetailMerchandise(
            @RequestParam Integer merchandiseId
    ){
        try{
            ShopMerchandiseInfoDTO shopMerchandiseInfoDTO = service.getMerchandiseInfo(merchandiseId);
            return ResponseEntity.status(HttpStatus.OK).body(shopMerchandiseInfoDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("shipper-options")
    public ResponseEntity<Object> getShipperOptions(){
        try{
            List<ShipperOptionDTO> shipperOptions = service.getShipperOptions();
            return ResponseEntity.status(HttpStatus.OK).body(shipperOptions);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("add-merchandise-to-cart")
    public ResponseEntity<Object> addMerchandiseToCart(
            @Valid @RequestBody ShopInsertUpdateDTO shopInsertUpdateDTO,
            BindingResult bindingResult
            ){
        try{
            if(bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getAllErrors());
            }
            service.saveToCart(shopInsertUpdateDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Add merchandise to cart success");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
