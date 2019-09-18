package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.Cat;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.CatRepository;
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

@Controller
public class GameController {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatService catService;
    @Autowired
    private UserService userService;
    @GetMapping("/cat")
    public String catList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("cats", catRepository.findAllByUser_Username(authentication.getName()));
        return "cat";
    }

    @PostMapping("/addcat")
    public String addCat (@RequestParam String catname, Model model){
        Cat cat = new Cat();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User currentUser = userService.getUserByUsername(userName);
        cat.setUser(currentUser);
        cat.setName(catname);
        cat.setCat_level(1);
        cat.setCat_expirience(0);
        catRepository.save(cat);
        model.addAttribute("cats", catRepository.findAllByUser_Username(authentication.getName()));
        return "cat";
    }


}
