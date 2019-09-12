package com.advanced.taracat.controllerview;

import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainUiController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getMainPage(@RequestParam(name = "username", required = false, defaultValue = "Game")
                                          String name, Model model){
        model.addAttribute("username", name);
        return "main";
    }


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/game")
    public  String chooseGame() {
        return "choose_game";
    }


}
