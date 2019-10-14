package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.Role;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.UserRepository;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
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
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userService.create(user);
        return "redirect:/login";
    }
}
