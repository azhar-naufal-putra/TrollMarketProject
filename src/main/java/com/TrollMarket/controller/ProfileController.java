package com.TrollMarket.controller;

import com.TrollMarket.dto.profile.ProfileHistoryGridDTO;
import com.TrollMarket.dto.profile.ProfileInfoDTO;
import com.TrollMarket.service.abstraction.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService service;

    @Autowired
    public ProfileController(ProfileService service){
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam String username,
            @RequestParam(defaultValue = "1") int page,
            Model model
    ){
        List<ProfileHistoryGridDTO> histories = service.getUserHistories(page, username);
        ProfileInfoDTO profile = service.getProfile(username);
        Long totalPages = service.getTotalPages(username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("profile", profile);
        model.addAttribute("profileHistoryGrid", histories);
        model.addAttribute("menuName", "PROFILE");
        return "profile/profile-index";
    }
}
