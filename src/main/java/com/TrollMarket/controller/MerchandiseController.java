package com.TrollMarket.controller;

import com.TrollMarket.dto.merchandise.MerchandiseGridDTO;
import com.TrollMarket.dto.merchandise.MerchandiseInsertUpdateDTO;
import com.TrollMarket.service.abstraction.MerchandiseService;
import com.TrollMarket.utility.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("merchandise")
public class MerchandiseController {
    private final MerchandiseService service;
    private final CategoryService categoryService;

    @Autowired
    public MerchandiseController(MerchandiseService service, CategoryService categoryService){
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam String username,
            Model model
    ){
        List<MerchandiseGridDTO> merchandises = service.getMerchandises(page, username);
        Long totalPages = service.getTotalPages(username);
        model.addAttribute("merchandiseGrid", merchandises);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("menuName", "MERCHANDISE");
        return "merchandise/merchandise-index";
    }

    @GetMapping("merchandise-form")
    public String insertUpdate(
            @RequestParam(required = false) Integer merchandiseId,
            Model model
    ){
        String action = merchandiseId != null ? "UPDATE" : "INSERT";
        MerchandiseInsertUpdateDTO merchandise = service.getMerchandise(merchandiseId);
        model.addAttribute("dto", merchandise);
        model.addAttribute("menuName", String.format("MERCHANDISE > %s PRODUCT", action));
        model.addAttribute("categoryOptions", categoryService.getCategoryOptions(merchandiseId));
        return "merchandise/merchandise-form";
    }

    @PostMapping("merchandise-form")
    public String insertUpdate(
            @Valid @ModelAttribute("dto") MerchandiseInsertUpdateDTO merchandiseInsertUpdateDTO,
            BindingResult bindingResult,
            Model model
    ){
        if (bindingResult.hasErrors()){
            String action = merchandiseInsertUpdateDTO != null ? "UPDATE" : "INSERT";
            model.addAttribute("menuName", String.format("MERCHANDISE > %s PRODUCT", action));
            model.addAttribute("categoryOptions", categoryService.getCategoryOptions(merchandiseInsertUpdateDTO.getMerchandiseId()));
            return "merchandise/merchandise-form";
        }else{
            service.save(merchandiseInsertUpdateDTO);
            return String.format("redirect:/merchandise?username=%s", merchandiseInsertUpdateDTO.getSellerUsername());
        }
    }

    @GetMapping("merchandise-delete")
    public String delete(
            @RequestParam Integer merchandiseId,
            @RequestParam String username
    ){
        service.delete(merchandiseId);
        return "redirect:/merchandise?username=" + username;
    }

    @GetMapping("merchandise-discontinue")
    public String discontinue(
            @RequestParam Integer merchandiseId,
            @RequestParam String username
    ){
        service.discontinue(merchandiseId);
        return "redirect:/merchandise?username=" + username;
    }
}
