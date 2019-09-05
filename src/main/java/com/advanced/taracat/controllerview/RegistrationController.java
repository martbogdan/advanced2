package com.advanced.taracat.controllerview;

import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
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
