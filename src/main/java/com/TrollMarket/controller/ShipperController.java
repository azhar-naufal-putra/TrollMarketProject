package com.TrollMarket.controller;

import com.TrollMarket.dto.shipper.ShipperGridDTO;
import com.TrollMarket.service.abstraction.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("shipper")
public class ShipperController {
    private final ShipperService service;

    @Autowired
    public ShipperController(ShipperService service){
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            Model model
    ){
        List<ShipperGridDTO> shippers = service.getShippers(page);
        Long totalPages = service.getTotalPages();
        model.addAttribute("shipperGrid", shippers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("menuName", "SHIPMENT");
        return "shipper/shipper-index";
    }

    @GetMapping("shipper-service")
    public String stopService(
            @RequestParam Integer shipperId
    ){
        service.stopService(shipperId);
        return "redirect:/shipper";
    }

    @GetMapping("shipper-delete")
    public String delete(
            @RequestParam Integer shipperId
    ){
        service.delete(shipperId);
        return "redirect:/shipper";
    }



}
