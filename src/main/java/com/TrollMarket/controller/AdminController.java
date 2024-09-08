package com.TrollMarket.controller;

import com.TrollMarket.dto.admin.AdminRegisterDTO;
import com.TrollMarket.service.abstraction.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin")
public class AdminController {
    private final AdminService service;

    @Autowired
    public AdminController(AdminService service){
        this.service = service;
    }

    @GetMapping("")
    public String adminForm(
        Model model
    ){
        model.addAttribute("role", "Admin");
        model.addAttribute("dto", new AdminRegisterDTO());
        model.addAttribute("menuName", "ADMIN");
        return "admin/admin-form";
    }

    @PostMapping("register")
    public String registerAdmin(
        @Valid @ModelAttribute("dto") AdminRegisterDTO adminRegisterDTO,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes
    ){
        if(bindingResult.hasErrors()){
            return "admin/admin-form";
        }else{
            service.registerAdmin(adminRegisterDTO);
            redirectAttributes.addFlashAttribute("message", "Save admin success!");
            return "redirect:/admin";
        }
    }
}
