package com.advanced.taracat.controllerview;

import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainUiController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getMainPage(@RequestParam(name = "name", required = false, defaultValue = "Game")
                                          String name, Model model){
        model.addAttribute("name", name);
        return "main";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("/registration")
    public String addUser (User user, Map<String,Object> model){
        User userDb = userService.getUserByUsername(user.getUserName());
        if (userDb != null){
            model.put("message","User exist");
            return "registration";
        }
        user.setActive(true);
        userService.create(user);
        return "redirect:/login";
    }

}
