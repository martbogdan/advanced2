package com.advanced.taracat.controllerview;

import com.advanced.taracat.dao.entity.Tarakan;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.CatRepository;
import com.advanced.taracat.dao.repository.TarakanRepository;
import com.advanced.taracat.dao.repository.UserRepository;
import com.advanced.taracat.service.CatService;
import com.advanced.taracat.service.TarakanService;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("status", "all");
        return "cat";
    }


}
