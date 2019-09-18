package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.Tarakan;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.TarakanRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import com.advanced.taracat.service.TarakanService;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TarakanController {
    @Autowired
    private TarakanRepository tarakanRepository;
    @Autowired
    private TarakanService tarakanService;
    @Autowired
    private UserService userService;
    @GetMapping("/tarlist")
    public String tarList(){return "tarlist";}

    @PostMapping("/tarlist")
    public String addTarakan (@RequestParam String tarname){
        Tarakan tarakan = new Tarakan();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User currentUser = userService.getUserByUsername(userName);
        tarakan.setUser(currentUser);
        tarakan.setTarname(tarname);
        tarakan.setLevel(0);
        tarakan.setExperience(0);
        tarakan.setStep(3);
        tarakanRepository.save(tarakan);
        return "tarlist";
    }
    @GetMapping("/list")
    public String getAllByUser (Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("tarakans", tarakanRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("status", "all");
        return "tarlist";
    }
    @GetMapping("/delete_tarakan")
    public String deleteTarakan (@RequestParam Long tarId){
        Tarakan deletedTarakan = tarakanRepository.findById(tarId).orElseThrow(NotFoundException::new);
        if (deletedTarakan != null){
            tarakanRepository.delete(deletedTarakan);
        }
         return "tarlist";
    }
}
