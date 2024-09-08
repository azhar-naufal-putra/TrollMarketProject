package com.TrollMarket.controller;

import com.TrollMarket.dto.account.AccountRegisterDTO;
import com.TrollMarket.service.abstraction.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("account")
public class AccountController {
    private final AccountService service;

    @Autowired
    public AccountController(AccountService service){
        this.service = service;
    }

    @GetMapping("login")
    public String login(){
        return "account/login-form";
    }

    @GetMapping("register")
    public String register(
            @RequestParam(required = false) String role,
            Model model
    ){
        model.addAttribute("dto", new AccountRegisterDTO());
        model.addAttribute("role", role);
        return "account/register-form";
    }

    @PostMapping("register")
    public String register(
            @Valid @ModelAttribute("dto") AccountRegisterDTO accountRegisterDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute("role", accountRegisterDTO.getRole());
            return "account/register-form";
        }else{
            service.register(accountRegisterDTO);
            redirectAttributes.addFlashAttribute("message", "Register Success");
            return "redirect:/account/login";
        }
    }

    @GetMapping("fail-login")
    public String loginFail(){
        return "account/fail-login";
    }
    @GetMapping("access-denied")
    public String accessDenied(){
        return "account/access-denied";
    }
}
