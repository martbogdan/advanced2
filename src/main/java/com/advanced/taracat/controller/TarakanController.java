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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

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
        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
        Tarakan tarakanBot = tarakanService.selectBotByUserLevel(tarId);

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
    @GetMapping("/choose_tarakan_six")
    public String chooseTarakanSix(@RequestParam Long tarId, Model model){
        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
//        Tarakan tarakanBot1 = tarakanService.selectRandomBotByUserLevel(tarId);
//        Tarakan tarakanBot2 = tarakanService.selectRandomBotByUserLevel(tarId);
//        Tarakan tarakanBot3 = tarakanService.selectRandomBotByUserLevel(tarId);
//        Tarakan tarakanBot4 = tarakanService.selectRandomBotByUserLevel(tarId);
//        Tarakan tarakanBot5 = tarakanService.selectRandomBotByUserLevel(tarId);

        String tarName = tarakanUser.getTarname();
        System.out.println("User tarakan: "+tarName);
//        System.out.println("BOT tarakan: "+ tarakanBot1.getTarname());
        model.addAttribute("tarakanName", tarName);
//        model.addAttribute("tarakanBotName1",tarakanBot1.getTarname());
//        model.addAttribute("tarakanBotName2",tarakanBot2.getTarname());
//        model.addAttribute("tarakanBotName3",tarakanBot3.getTarname());
//        model.addAttribute("tarakanBotName4",tarakanBot4.getTarname());
//        model.addAttribute("tarakanBotName5",tarakanBot5.getTarname());


        model.addAttribute("tarakanUser", tarakanUser);
//        model.addAttribute("tarakanBot1", tarakanBot1);
//        model.addAttribute("tarakanBot2", tarakanBot2);
//        model.addAttribute("tarakanBot3", tarakanBot3);
//        model.addAttribute("tarakanBot4", tarakanBot4);
//        model.addAttribute("tarakanBot5", tarakanBot5);

        model.addAttribute("tarId",tarakanUser.getId());

        return "tar_six";
    }

    @GetMapping ("/run_tarakan")
    public String runTarakan (@RequestParam Long tarId, @RequestParam Long tarBotId, Model model){

        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
        Tarakan tarakanBot = tarakanService.selectBotByUserLevel(tarId);

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
            tarakanService.updateLevel(tarakanUser, 1);
            tarakanService.updateWin(tarakanUser);
        }else  if (wayUser < wayBot){
            winner = tarakanBot.getTarname();
            tarakanService.updateLoss(tarakanUser);
        }else {
            winner = "Friendship won!!!";
            tarakanService.updateDraw(tarakanUser);
        }
        tarakanService.updateRuning(tarakanUser);
        model.addAttribute("winner", winner);
        model.addAttribute("tarId",tarakanUser.getId());
        model.addAttribute("tarBotId",tarakanBot.getId());
        return "tar";
    }
    @GetMapping ("/run_tarakan_six")
    public String runTarakanSix (@RequestParam Long tarId, Model model){

        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
        ArrayList<Tarakan> tarakans = (ArrayList<Tarakan>) tarakanService.generateTarakanBotsByUserLevel(tarId,5);
        System.out.println(tarakans);
        Tarakan tarakanBot1 = tarakanService.selectRandomBotByUserLevel(tarId);
        Tarakan tarakanBot2 = tarakanService.selectRandomBotByUserLevel(tarId);
        Tarakan tarakanBot3 = tarakanService.selectRandomBotByUserLevel(tarId);
        Tarakan tarakanBot4 = tarakanService.selectRandomBotByUserLevel(tarId);
        Tarakan tarakanBot5 = tarakanService.selectRandomBotByUserLevel(tarId);

        String[] names = {"Oggi", "Jeck", "Joi", "Di-di", "Marki"};

        Random rnd = new Random();
        List<String> freeNames = null;
        String tName1 = null, tName2=null,tName3=null,tName4=null,tName5=null;
        for (int i = 0; i < names.length; i++) {
            if (freeNames == null || freeNames.size() == 0) {
                freeNames = new ArrayList<>(Arrays.asList(names));
            }
            tName1 = freeNames.remove(rnd.nextInt(freeNames.size()));
            tName2 = freeNames.remove(rnd.nextInt(freeNames.size()));
            tName3 = freeNames.remove(rnd.nextInt(freeNames.size()));
            tName4 = freeNames.remove(rnd.nextInt(freeNames.size()));
            tName5 = freeNames.remove(rnd.nextInt(freeNames.size()));
        }

            tarakanBot1.setTarname(tName1+" "+tarakanBot1.getTarname());
            tarakanBot2.setTarname(tName2+" "+tarakanBot2.getTarname());
            tarakanBot3.setTarname(tName3+" "+tarakanBot3.getTarname());
            tarakanBot4.setTarname(tName4+" "+tarakanBot4.getTarname());
            tarakanBot5.setTarname(tName5+" "+tarakanBot5.getTarname());


        String tarName = tarakanUser.getTarname();
        model.addAttribute("tarakanName", tarName);
        model.addAttribute("tarakanBotName1",tarakanBot1.getTarname());
        model.addAttribute("tarakanBotName2",tarakanBot2.getTarname());
        model.addAttribute("tarakanBotName3",tarakanBot3.getTarname());
        model.addAttribute("tarakanBotName4",tarakanBot4.getTarname());
        model.addAttribute("tarakanBotName5",tarakanBot5.getTarname());
        Random random = new Random();
        int wayUser = 0; int wayBot1 = 0; int wayBot2 = 0; int wayBot3 = 0; int wayBot4 = 0; int wayBot5 = 0;
        int stepUser, stepBot1, stepBot2, stepBot3, stepBot4, stepBot5;
        List<Integer> wayU = new ArrayList<>();
        List<Integer> wayB = new ArrayList<>();
        boolean isFinish = true;
        while (isFinish){
            stepUser = random.nextInt(tarakanUser.getStep()+1);
            stepBot1 = random.nextInt(tarakanBot1.getStep()+1);
            stepBot2 = random.nextInt(tarakanBot2.getStep()+1);
            stepBot3 = random.nextInt(tarakanBot3.getStep()+1);
            stepBot4 = random.nextInt(tarakanBot4.getStep()+1);
            stepBot5 = random.nextInt(tarakanBot5.getStep()+1);
            wayUser=wayUser+stepUser;
            wayBot1=wayBot1+stepBot1;
            wayBot2=wayBot2+stepBot2;
            wayBot3=wayBot3+stepBot3;
            wayBot4=wayBot4+stepBot4;
            wayBot5=wayBot5+stepBot5;
            wayU.add(wayUser);
            wayB.add(wayBot1);
            if (wayUser >= 100 || wayBot1 >= 100 || wayBot2 >= 100 ||wayBot3 >= 100 ||wayBot4 >= 100 ||wayBot5 >= 100){
                isFinish = false;
            }
        }
        System.out.println("User: "+wayU);
        System.out.println("BOT:  "+wayB);
        System.out.println("User: "+wayUser);
        System.out.println("BOT:  "+wayBot1);

        model.addAttribute("wayUser",wayUser);
        model.addAttribute("wayBot1",wayBot1);
        model.addAttribute("wayBot2",wayBot2);
        model.addAttribute("wayBot3",wayBot3);
        model.addAttribute("wayBot4",wayBot4);
        model.addAttribute("wayBot5",wayBot5);

        HashMap<String,Integer> map = new HashMap<>();
        map.put(tarakanUser.getTarname(), wayUser);
        map.put(tarakanBot1.getTarname(), wayBot1);
        map.put(tarakanBot2.getTarname(), wayBot2);
        map.put(tarakanBot3.getTarname(), wayBot3);
        map.put(tarakanBot4.getTarname(), wayBot4);
        map.put(tarakanBot5.getTarname(), wayBot5);
        System.out.println(map);

            List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            Map<String, Integer> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : entries){
                sortedMap.put(entry.getKey(), entry.getValue());
            }
        System.out.println(sortedMap);
        System.out.println(sortedMap.values());
        model.addAttribute("place",sortedMap);
//        String winner;
//        if (wayUser > wayBot){
//            winner = tarakanUser.getTarname();
//            tarakanService.updateLevel(tarakanUser);
//            tarakanService.updateWin(tarakanUser);
//        }else  if (wayUser < wayBot){
//            winner = tarakanBot.getTarname();
//            tarakanService.updateLoss(tarakanUser);
//        }else {
//            winner = "Friendship won!!!";
//            tarakanService.updateDraw(tarakanUser);
//        }
//        tarakanService.updateRuning(tarakanUser);
//        model.addAttribute("winner", winner);
        model.addAttribute("tarId",tarakanUser.getId());
//        model.addAttribute("tarBotId",tarakanBot.getId());
        return "tar_six";
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
