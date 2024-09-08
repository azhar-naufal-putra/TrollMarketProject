package com.TrollMarket.controller;

import com.TrollMarket.dto.history.HistoryBuyerOptionDTO;
import com.TrollMarket.dto.history.HistoryGridDTO;
import com.TrollMarket.dto.history.HistorySellerOptionDTO;
import com.TrollMarket.service.abstraction.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("history")
public class HistoryController {
    private final HistoryService service;

    @Autowired
    public HistoryController(HistoryService service){
        this.service = service;
    }

    @GetMapping("")
    public String historyOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String buyerUsername,
            @RequestParam(defaultValue = "") String sellerUsername,
            Model model
    ){
        List<HistoryBuyerOptionDTO> buyers = service.getBuyerOptions();
        List<HistorySellerOptionDTO> sellers = service.getSellerOptions();
        List<HistoryGridDTO> histories = service.getAllHistories(page, buyerUsername, sellerUsername);
        Long totalPages = service.getTotalPages(buyerUsername, sellerUsername);
        model.addAttribute("buyerOptions", buyers);
        model.addAttribute("sellerOptions", sellers);
        model.addAttribute("historyGrid", histories);
        model.addAttribute("currentPage", page);
        model.addAttribute("buyerUsername", buyerUsername);
        model.addAttribute("sellerUsername", sellerUsername);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("menuName", "HISTORY");
        return "history/history-index";
    }
}
