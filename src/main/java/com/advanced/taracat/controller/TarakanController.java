package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.Tarakan;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.TarakanRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import com.advanced.taracat.service.TarakanService;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TarakanController {

    public static final String TARAKANS_LIMIT_MESSAGE = "У вас вже існує 5 тараканів, видаліть одного або декілька та добавте нового";
    public static final String TARAKAN_NAME_EXISTS_MESSAGE = "Таракан з таким іменем вже існує";

    @Autowired
    private TarakanRepository tarakanRepository;
    @Autowired
    private TarakanService tarakanService;
    @Autowired
    private UserService userService;

    @GetMapping("/tarlist")
    public String listTarakan (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("tarakans", tarakanRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("newTarakan", new Tarakan());
        return "tarlist";
    }

    @PostMapping("/addtar")
    public String addTarakan (@ModelAttribute("newTarakan") Tarakan newTarakan, RedirectAttributes model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByUsername(userName);

        boolean error = false;
        List<Tarakan> tarCount = tarakanService.getAllByUsername(user.getUsername());
        int tarCountSize = tarCount.size();
        if (tarCountSize >= 5) {
            model.addFlashAttribute("tar_error", TARAKANS_LIMIT_MESSAGE);
            error = true;
        }
        Tarakan tarakanDB = tarakanService.getTarakanByName(newTarakan.getTarname());
        if (tarakanDB != null) {
            model.addFlashAttribute("tar_error", TARAKAN_NAME_EXISTS_MESSAGE);
            error = true;
        }

        if (!error) {
            tarakanService.create(newTarakan.getTarname(), user);
        }

        return "redirect:/tarlist";
    }

    @GetMapping("/delete_tarakan")
    public String deleteTarakan (@RequestParam Long tarId){
        Tarakan deletedTarakan = tarakanRepository.findById(tarId).orElseThrow(NotFoundException::new);
        if (deletedTarakan != null){
            tarakanRepository.delete(deletedTarakan);
        }
         return "forward:/tarlist";
    }
    @GetMapping("/choose_tarakan")
    public String chooseTarakan(){
        return "tar";
    }
}
