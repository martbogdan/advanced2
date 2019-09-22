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

import java.util.List;

@Controller
public class GameController {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatService catService;
    @Autowired
    private UserService userService;

    //Метод для отримування списку котів юзера
    @GetMapping("/cat")
    public String catList(Model model){
        String catError = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("cats", catRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("cat_error", catError);
        return "cat";
    }
//          Метод додавання кота
    @PostMapping("/addcat")
    public String addCat (@RequestParam String catname, Model model){
        Cat cat = new Cat();
        Cat catDB = new Cat();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User currentUser = userService.getUserByUsername(userName);

        String catError;

        List<Cat> catCount = catRepository.findAllByUser_Username(currentUser.getUsername());
        int catCountSize = catCount.size();
        if (catCountSize >= 5) {
            catError = "У вас вже існує 5 котів, видаліть одного або декілька та добавте нового";
        } else {
            catDB = catRepository.findCatByName(catname);

            if (catDB != null) {
                catError = "Кіт з таким іменем вже існує";
            } else {
                cat.setUser(currentUser);
                cat.setName(catname);
                cat.setCat_level(1);
                cat.setCat_expirience(0);
                catRepository.save(cat);
                catError = "";
            }
        }

        model.addAttribute("cats", catRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("cat_error", catError);
        return "cat";
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

}
/*
Попередній коміт*/
