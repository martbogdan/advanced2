package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.Cat;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.CatRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import com.advanced.taracat.service.CatService;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class CatController {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatService catService;
    @Autowired
    private UserService userService;

    // Отримання списку котів
    @GetMapping("/cat")
    public String catList(Model model){
        String catError = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("cats", catRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("cat_error", catError);
        return "cat";
    }

    // Дбавлення кота
    @PostMapping("/addcat")
    public String addCat (@RequestParam String catname, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        User currentUser = userService.getUserByUsername(userName);

        Cat catDB;

        String catError;

        List<Cat> catCount = catRepository.findAllByUser_Username(currentUser.getUsername());
        int catCountSize = catCount.size();
        if (catCountSize >= 5) {
            catError = "У вас вже існує 5 котів, видаліть одного або декілька котів та добавте нового";
        } else {
            catDB = catRepository.findCatByName(catname);

            if (catDB != null) {
                catError = "Кіт з таким іменем вже існує";
            } else {
                catService.addCat(catname, userName);
                catError = "";
            }
        }

        model.addAttribute("cats", catRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("cat_error", catError);

        return "cat";
    }


    @PostMapping("catfight")
    public String catFight (@RequestParam Long catid, @RequestParam int catbot, Model model){
        Cat cat = new Cat();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User currentUser = userService.getUserByUsername(userName);

        cat = catRepository.findCatById(catid);

        int cat_Hp = cat.getCat_level() * 10;
        int catLast_Hp = cat.getCat_level() * 10;
        int catBot_Hp = catbot * 10;
        int catBotLast_Hp = catbot * 10;


        model.addAttribute("catid", cat.getId());
        model.addAttribute("catname", cat.getName());
        model.addAttribute("catlevel", cat.getCat_level());
        model.addAttribute("cathpstart", cat_Hp);
        model.addAttribute("cathpfinish", catLast_Hp);

        model.addAttribute("catbotname", "Кіт Бот "+catbot+"-ого рівня");
        model.addAttribute("catbot", catbot);
        model.addAttribute("catbothpstart", catBot_Hp);
        model.addAttribute("catbothpfinish", catBotLast_Hp);

        return "cat_fight";
    }

    @PostMapping("catfightkick")
    public String catFight (@RequestParam Long catid, @RequestParam int catattack, @RequestParam int catdeff, @RequestParam Long catbot, @RequestParam int catbotid, @RequestParam int cathpfinish, @RequestParam int catbothpfinish, Model model){

        Cat cat = new Cat();
        Random random = new Random();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User currentUser = userService.getUserByUsername(userName);

        cat = catRepository.findCatById(catid);

        int catbotattack = random.nextInt(3)+1;
        int catbotdeff = random.nextInt(3)+1;

        int catStright = random.nextInt(cat.getCat_level())+1;
        int catBotStright = random.nextInt(catbotid)+1;

        String catMessageFormation = "";
        String catMessageFormation2 = "";

        if (catattack == catbotdeff) {
            catMessageFormation = cat.getName() + " попав в блок";
        } else {
            catbothpfinish = catbothpfinish-catStright;
            catMessageFormation = cat.getName() + " вдарив на -" + catStright + " життя";
        }

        if (catbotattack == catdeff) {
            catMessageFormation2 = "Кіт Бот попав в блок";
        } else {
            cathpfinish = cathpfinish-catBotStright;
            catMessageFormation2 = "Кіт Бот вдарив на -" + catBotStright + " життя";
        }

        int cat_Hp = cat.getCat_level() * 10;
        int catLast_Hp = cathpfinish;

        Long catBot_Hp = catbot * 10;
        int catBotLast_Hp = catbothpfinish;

        if (cathpfinish <= 0 && catbothpfinish >= 0) {
            return "cat_loose";
        } else if (catbothpfinish <= 0 && cathpfinish >= 0) {

            int catexpirience = 100;

            if (cat.getCat_level() > catbotid) {
                if ((cat.getCat_level() - catbotid) == 1) {
                    catexpirience = catexpirience / 2;
                } else {
                    catexpirience = catexpirience / (cat.getCat_level() - catbotid);
                }
            } else if (cat.getCat_level() < catbotid) {
                if ((cat.getCat_level() - catbotid) == 1) {
                    catexpirience = catexpirience * 2;
                } else {
                    catexpirience = catexpirience * (cat.getCat_level() - catbotid);
                }
            }

            int exp = cat.getCat_expirience();
            cat.setCat_expirience(catexpirience+exp);

            catService.updateExpirience(cat);

            model.addAttribute("catexpirience", catexpirience);
            return "cat_win";
        }


        model.addAttribute("catid", cat.getId());
        model.addAttribute("catname", cat.getName());
        model.addAttribute("catlevel", cat.getCat_level());
        model.addAttribute("cathpstart", cat_Hp);
        model.addAttribute("cathpfinish", catLast_Hp);

        model.addAttribute("catbot", catbot);
        model.addAttribute("catbotname", "Кіт Бот "+catbot+"-ого рівня");
        model.addAttribute("catbothpstart", catBot_Hp);
        model.addAttribute("catbothpfinish", catBotLast_Hp);

        model.addAttribute("catmessage", catMessageFormation);
        model.addAttribute("catmessage2", catMessageFormation2);

        return "cat_fight";

    }

    // Метод видалення кота.
    @GetMapping("/delete_cat")
    public String deleteCat (@RequestParam Long catId) {
        Cat deleteCat = catRepository.findById(catId).orElseThrow(NotFoundException::new);
        if (deleteCat != null) {
            catRepository.delete(deleteCat);
        }
        return "forward:/cat";
    }

    @GetMapping("/labirynth")
    public String labList(@RequestParam Long catId, Model model){
        /*String catError = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("cats", catRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("cat_error", catError);*/
        return "labirynth";
    }

}
/*
Попередній коміт*/
