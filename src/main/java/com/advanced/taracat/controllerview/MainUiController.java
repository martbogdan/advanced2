package com.advanced.taracat.controllerview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainUiController {

    @GetMapping("/")
    public String getMainPage(@RequestParam(name = "name", required = false, defaultValue = "Game")
                                          String name, Model model){
        model.addAttribute("name", name);
        return "hello";
    }
}
