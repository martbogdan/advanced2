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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public String chooseTarakan(@RequestParam Long tarId, Model model){
        Tarakan tarakanBot = new Tarakan();
        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
        if (tarakanUser.getLevel()==1){
            tarakanBot = tarakanService.getTarakanByName("bot1");
        } else if (tarakanUser.getLevel()==2){
            tarakanBot = tarakanService.getTarakanByName("bot2");
        } else if (tarakanUser.getLevel()==3){
            tarakanBot = tarakanService.getTarakanByName("bot3");
        }
        String tarName = tarakanUser.getTarname();
        System.out.println("User tarakan: "+tarName);
        System.out.println("BOT tarakan: "+ tarakanBot.getTarname());
        model.addAttribute("tarakanName", tarName);
        model.addAttribute("tarakanBotName",tarakanBot.getTarname());


        model.addAttribute("tarakanUser", tarakanUser);
        model.addAttribute("tarakanBot", tarakanBot);

        model.addAttribute("tarId",tarakanUser.getId());
        model.addAttribute("tarBotId",tarakanBot.getId());
        return "tar";
    }

    @GetMapping ("/run_tarakan")
    public String runTarakan (@RequestParam Long tarId, @RequestParam Long tarBotId, Model model){

        Tarakan tarakanBot = new Tarakan();
        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
        if (tarakanUser.getLevel()==1){
            tarakanBot = tarakanService.getTarakanByName("bot1");
        } else if (tarakanUser.getLevel()==2){
            tarakanBot = tarakanService.getTarakanByName("bot2");
        } else if (tarakanUser.getLevel()==3){
            tarakanBot = tarakanService.getTarakanByName("bot3");
        }
        String tarName = tarakanUser.getTarname();
        model.addAttribute("tarakanName", tarName);
        model.addAttribute("tarakanBotName",tarakanBot.getTarname());
        Random random = new Random();
        int wayUser = 0;
        int wayBot = 0;
        int stepUser, stepBot;
        List<Integer> wayU = new ArrayList<>();
        List<Integer> wayB = new ArrayList<>();
        boolean isFinish = true;
        while (isFinish){
            stepUser = random.nextInt(tarakanUser.getStep()+1);
            stepBot = random.nextInt(tarakanBot.getStep()+1);
            wayUser=wayUser+stepUser;
            wayBot=wayBot+stepBot;
            wayU.add(wayUser);
            wayB.add(wayBot);
            if (wayUser >= 100 || wayBot >= 100){
                isFinish = false;
            }
        }
        System.out.println("User: "+wayU);
        System.out.println("BOT:  "+wayB);
        System.out.println("User: "+wayUser);
        System.out.println("BOT:  "+wayBot);

        model.addAttribute("wayUser",wayUser);
        model.addAttribute("wayBot",wayBot);
        String winner;
        if (wayUser > wayBot){
            winner = tarakanUser.getTarname();
        }else  if (wayUser < wayBot){
            winner = tarakanBot.getTarname();
        }else {
            winner = "Friendship won!!!";
        }
        model.addAttribute("winner", winner);
        model.addAttribute("tarId",tarakanUser.getId());
        model.addAttribute("tarBotId",tarakanBot.getId());
        return "forward:/tar";
    }
    @GetMapping ("/reload")
    public String reload (){
        return "redirect:/tar";
    }
    @GetMapping ("/back_to_list")
    public String backToList (){
        return "redirect:/tarlist";
    }
}
