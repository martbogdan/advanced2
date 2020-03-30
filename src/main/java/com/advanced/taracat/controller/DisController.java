package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.Hero;
import com.advanced.taracat.dao.entity.Location;
import com.advanced.taracat.dao.repository.DisRepository;
import com.advanced.taracat.service.DisService;
import com.advanced.taracat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class DisController {

    @Autowired
    private DisRepository disRepository;
    @Autowired
    private DisService disService;
    @Autowired
    private UserService userService;

    @GetMapping("/heroList")
    public String listOfHero(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("heroes", disService.getAllActiveByUsername(authentication.getName()));
        model.addAttribute("newHero", new Hero());
        return "hero";
    }

    // Добавлення героя
    @PostMapping("/addhero")
    public String addHero(@ModelAttribute("newHero") Hero newHero, RedirectAttributes model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        Hero heroDB;
        String heroError = "";
        boolean error = false;
        List<Hero> heroCount = disService.getAllActiveByUsername(authentication.getName());
        int heroCountSize = heroCount.size();
        if (heroCountSize >= 15) {
            heroError = "У вас вже існує 15 героїв";
            error = true;
        }
        heroDB = disService.getHeroByName(newHero.getName());
        if (newHero.getName().trim().isEmpty()) {
            heroError = "Герой повинен мати імя";
            error = true;
        }
        if (newHero.getName().length() > 21) {
            heroError = "Імя Героя не повинно бути більшим 20 букв!";
            error = true;
        }
        if (heroDB != null) {
            heroError = "Герой з таким іменем вже існує";
            error = true;
        }
        if (!error) {
            disService.addHero(newHero.getName(), userName, newHero.getRace(), newHero.getHeroClass());
            heroError = "";
        }
        model.addFlashAttribute("hero_error", heroError);
        log.info(heroError);
        return "redirect:/heroList";
    }

    @GetMapping("/choose_hero")
    public String heroZone(@RequestParam Long heroId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*List<Location> locations = disService.getAllLocations();*/

        String heroError = "";

        int userZoneX = 1;
        int userZoneY = 1;

        Hero hero = disService.getHeroById(heroId);
        if (hero != null) {
            userZoneX = hero.getZoneX();
            userZoneY = hero.getZoneY();
        }

        model.addAttribute("userZoneX", disService.findZone(userZoneX));
        model.addAttribute("userZoneY", disService.findZone(userZoneY));
        model.addAttribute("heroId", heroId);
        model.addAttribute("heroError", heroError);

        return "zone";
    }

    @GetMapping("/zone")
    public String heroZone(@RequestParam Long heroId, int zoneX, int zoneY, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String heroError = "";

        int testZoneX = 0;
        int testZoneY = 0;

        Hero heroCheck = disService.getHeroById(heroId);

        if (heroCheck.getZoneX() > zoneX) {
            testZoneX = heroCheck.getZoneX()-zoneX;
        } else {
            testZoneX = zoneX-heroCheck.getZoneX();
        }

        if (heroCheck.getZoneY() > zoneY) {
            testZoneY = heroCheck.getZoneY()-zoneY;
        } else {
            testZoneY = zoneY-heroCheck.getZoneY();
        }

        if (testZoneY > 1 || testZoneX > 1) {

            heroError = "Ви намагаєтесь переміститись більше ніж на 1 клітинку";

            model.addAttribute("userZoneX", disService.findZone(heroCheck.getZoneX()));
            model.addAttribute("userZoneY", disService.findZone(heroCheck.getZoneY()));

        } else {

            disService.updateZone(heroId, zoneX, zoneY);

            Hero hero = disService.getHeroById(heroId);

            model.addAttribute("userZoneX", disService.findZone(hero.getZoneX()));
            model.addAttribute("userZoneY", disService.findZone(hero.getZoneY()));

        }

        model.addAttribute("heroId", heroId);
        model.addAttribute("heroError", heroError);

        return "zone";
    }

    @GetMapping("/hero_settings")
    public String heroSettings(@RequestParam(value = "heroId") Long heroId, Model model) {
        model.addAttribute("hero", disService.getHeroById(heroId));
        return "hero_settings";
    }

    @GetMapping("/delete_hero_forever")
    public String deleteHero(@RequestParam Long heroId) {
        Hero hero = disService.getHeroById(heroId);
        if (hero != null) {
            log.info("Hero: " + hero.getName() + " deleted forever");
            disService.delete(hero.getId());
        }
        return "redirect:/heroList";
    }

    @GetMapping("delete_hero")
    public String deleteHeroMark(@RequestParam Long heroId) {
        Hero hero = disService.getHeroById(heroId);
        if (hero != null) {
            disService.deleteMark(hero.getId());
            log.info("Hero: " + hero.getName() + " was marked (deleted)");
        }
        return "redirect:/heroList";
    }


}