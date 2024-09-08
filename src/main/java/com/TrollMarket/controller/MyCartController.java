package com.TrollMarket.controller;

import com.TrollMarket.dto.myCart.MyCartGridDTO;
import com.TrollMarket.service.abstraction.MyCartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("my-cart")
public class MyCartController {
    private final MyCartService service;

    @Autowired
    public MyCartController(MyCartService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam String username,
            @RequestParam(defaultValue = "1") int page,
            Model model
    ){
        MyCartGridDTO myCart = service.getMyCart(page, username);
        Long totalPages = service.getTotalPages(username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("myCartGrid", myCart);
        model.addAttribute("menuName", "MY CART");
        return "my-cart/my-cart-index";
    }

    @GetMapping("remove-order")
    public String removeOrder(
            @RequestParam Integer orderId,
            @RequestParam Integer merchandiseId,
            @RequestParam String username,
            RedirectAttributes redirectAttributes
    ){
        service.removeOrderDetail(orderId, merchandiseId);
        redirectAttributes.addFlashAttribute("message", "Remove Successful");
        return "redirect:/my-cart?username="+username;
    }
}
