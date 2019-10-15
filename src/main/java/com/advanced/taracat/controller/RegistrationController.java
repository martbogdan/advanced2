package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.UserRepository;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser (User user, Map<String,Object> model){
        User userDb = userService.getUserByUsername(user.getUsername());
        if (userDb != null){
            model.put("message","User exist");
            return "registration";
        }
        if (user.getUsername().trim().isEmpty()){
            model.put("message", "Hey!!! Type any symbol!!!");
            return "registration";
        }
        if (user.getPassword().trim().isEmpty()){
            model.put("message", "It's not a good password!!! Please, type a good password and never forget it");
            return "registration";
        }
        userService.create(user);
        return "redirect:/login";
    }
}
