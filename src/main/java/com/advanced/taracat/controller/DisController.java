package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.Hero;
import com.advanced.taracat.dao.repository.DisRepository;
import com.advanced.taracat.service.DisService;
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
public class DisController {

    @Autowired
    private DisRepository disRepository;
    @Autowired
    private DisService disService;
    @Autowired
    private UserService userService;

    // Добавлення героя
    @PostMapping("/addhero")
    public String addHero (@RequestParam String heroname, int herorace, int heroclass, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        Hero heroDB;

        String heroError;

        List<Hero> heroCount = disService.getAllByUsername(authentication.getName());
        int heroCountSize = heroCount.size();
        if (heroCountSize >= 15) {
            heroError = "У вас вже існує 15 героїв";
        } else {

            heroDB = disService.getHeroByName(heroname);

            if (heroDB != null) {
                heroError = "Герой з таким іменем вже існує";
            } else {
                disService.addHero(heroname, userName, herorace, heroclass);
                heroError = "";
            }
        }

        model.addAttribute("heroes", disService.getAllByUsername(authentication.getName()));
        model.addAttribute("hero_error", heroError);

        return "hero";
    }

    @GetMapping("/choose_hero")
    public String heroZone(@RequestParam Long heroId, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String heroError = "";

        //model.addAttribute("cats", catService.getAllByUsername(authentication.getName()));
        //model.addAttribute("cat_error", catError);
        return "zone";
    }



}