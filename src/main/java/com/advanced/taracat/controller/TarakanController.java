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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TarakanController {
    @Autowired
    private TarakanRepository tarakanRepository;
    @Autowired
    private TarakanService tarakanService;
    @Autowired
    private UserService userService;

    @GetMapping("/tarlist")
    public String listTarakan (Model model) {
        String tarError = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("tarakans", tarakanRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("tar_error", tarError);
        return "tarlist";
    }

    @PostMapping("/addtar")
    public String addTarakan (@RequestParam String tarname, Model model){
        Tarakan tarakan = new Tarakan();
        Tarakan tarakanDB;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User currentUser = userService.getUserByUsername(userName);

        String tarError;

        List<Tarakan> tarCount = tarakanRepository.findAllByUser_Username(userName);
        int tarCountSize = tarCount.size();
        if (tarCountSize >= 5){
            tarError = "У вас вже існує 5 тараканів, видаліть одного або декілька та добавте нового";
        } else {
            tarakanDB = tarakanRepository.findByTarname(tarname);
            if(tarakanDB != null){
                tarError = "Таракан з таким іменем вже існує";
            } else {
                tarakan.setUser(currentUser);
                tarakan.setTarname(tarname);
                tarakan.setLevel(1);
                tarakan.setExperience(0);
                tarakan.setStep(3);
                tarakanRepository.save(tarakan);
                tarError = "";
            }
        }
        model.addAttribute("tarakans", tarakanRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("tar_error", tarError);
        return "tarlist";
    }

    @GetMapping("/delete_tarakan")
    public String deleteTarakan (@RequestParam Long tarId){
        Tarakan deletedTarakan = tarakanRepository.findById(tarId).orElseThrow(NotFoundException::new);
        if (deletedTarakan != null){
            tarakanRepository.delete(deletedTarakan);
        }
         return "forward:/tarlist";
    }
}
