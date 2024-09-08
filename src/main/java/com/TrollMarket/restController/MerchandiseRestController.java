package com.TrollMarket.restController;

import com.TrollMarket.dto.merchandise.MerchandiseInfoDTO;
import com.TrollMarket.service.abstraction.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/merchandise")
public class MerchandiseRestController {
    private final MerchandiseService service;

    @Autowired
    public MerchandiseRestController(MerchandiseService service){
        this.service = service;
    }

    @GetMapping("merchandise-info/{merchandiseId}")
    public ResponseEntity<Object> getInfo(
            @PathVariable Integer merchandiseId
    ){
        try{
            MerchandiseInfoDTO merchandiseInfoDTO = service.getMerchandiseInfo(merchandiseId);
            return ResponseEntity.status(HttpStatus.OK).body(merchandiseInfoDTO);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
