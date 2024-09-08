package com.TrollMarket.controller;

import com.TrollMarket.dto.shop.ShopGridDTO;
import com.TrollMarket.service.abstraction.ShopService;
import com.TrollMarket.utility.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("shop")
public class ShopController {
    private final ShopService service;
    private final CategoryService categoryService;

    @Autowired
    public ShopController(ShopService service, CategoryService categoryService){
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String merchandiseName,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "") String merchandiseDescription,
            Model model
    ){
        List<ShopGridDTO> merchandises = service.getMerchandises(page, merchandiseName, categoryId,  merchandiseDescription);
        Long totalPages = service.getTotalPages(merchandiseName, categoryId, merchandiseDescription);
        model.addAttribute("shopGrid", merchandises);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("merchandiseName", merchandiseName);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryOptions", categoryService.getCategoryOptionByCategoryId(categoryId));
        model.addAttribute("merchandiseDescription", merchandiseDescription);
        model.addAttribute("menuName", "SHOP");
        return "shop/shop-index";
    }
}
