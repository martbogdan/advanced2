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

import java.util.*;

@Controller
public class TarakanController {

    public static final String TARAKANS_LIMIT_MESSAGE = "You already have 5 cockroaches, delete one or more and then add new cockroach";
    public static final String TARAKAN_NAME_EXISTS_MESSAGE = "Cockroach with this name already exists";
    public static final String TARAKAN_NAME_NOT_BLANK = "Cockroaches name must contain at least 1 character";
    @Autowired
    private TarakanRepository tarakanRepository;
    @Autowired
    private TarakanService tarakanService;
    @Autowired
    private UserService userService;

    @GetMapping("/tarlist")
    public String listTarakan(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("tarakans", tarakanRepository.findAllByUser_Username(authentication.getName()));
        model.addAttribute("newTarakan", new Tarakan());
        return "tarlist";
    }

    @PostMapping("/addtar")
    public String addTarakan(@ModelAttribute("newTarakan") Tarakan newTarakan, RedirectAttributes model) {
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
        if (newTarakan.getTarname().trim().isEmpty()){
            model.addFlashAttribute("tar_error",TARAKAN_NAME_NOT_BLANK);
            error = true;
        }

        if (!error) {
            tarakanService.create(newTarakan.getTarname(), user);
        }

        return "redirect:/tarlist";
    }

    @GetMapping("/delete_tarakan")
    public String deleteTarakan(@RequestParam Long tarId) {
        Tarakan deletedTarakan = tarakanRepository.findById(tarId).orElseThrow(NotFoundException::new);
        if (deletedTarakan != null) {
            tarakanRepository.delete(deletedTarakan);
        }
        return "forward:/tarlist";
    }

    @GetMapping("/choose_tarakan")
    public String chooseTarakan(@RequestParam Long tarId, Model model) {
        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
        Tarakan tarakanBot = tarakanService.selectBotByUserLevel(tarId);

        String tarName = tarakanUser.getTarname();
        System.out.println("User tarakan: " + tarName);
        System.out.println("BOT tarakan: " + tarakanBot.getTarname());
        model.addAttribute("tarakanName", tarName);
        model.addAttribute("tarakanBotName", tarakanBot.getTarname());


        model.addAttribute("tarakanUser", tarakanUser);
        model.addAttribute("tarakanBot", tarakanBot);

        model.addAttribute("tarId", tarakanUser.getId());
        model.addAttribute("tarBotId", tarakanBot.getId());
        return "tar";
    }

    @GetMapping("/choose_tarakan_six")
    public String chooseTarakanSix(@RequestParam Long tarId, Model model) {
        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);

        String tarName = tarakanUser.getTarname();
        model.addAttribute("tarakanName", tarName);
        model.addAttribute("tarakanUser", tarakanUser);
        model.addAttribute("tarId", tarakanUser.getId());

        return "tar_six";
    }

    @GetMapping("/run_tarakan")
    public String runTarakan(@RequestParam Long tarId, @RequestParam Long tarBotId, Model model) {

        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
        Tarakan tarakanBot = tarakanService.selectBotByUserLevel(tarId);

        String tarName = tarakanUser.getTarname();
        model.addAttribute("tarakanName", tarName);
        model.addAttribute("tarakanBotName", tarakanBot.getTarname());
        Random random = new Random();
        int wayUser = 0;
        int wayBot = 0;
        int stepUser, stepBot;
        List<Integer> wayU = new ArrayList<>();
        List<Integer> wayB = new ArrayList<>();
        boolean isFinish = true;
        while (isFinish) {
            stepUser = random.nextInt(tarakanUser.getStep() + 1);
            stepBot = random.nextInt(tarakanBot.getStep() + 1);
            wayUser = wayUser + stepUser;
            wayBot = wayBot + stepBot;
            wayU.add(wayUser);
            wayB.add(wayBot);
            if (wayUser >= 100 || wayBot >= 100) {
                isFinish = false;
            }
        }
        System.out.println("User: " + wayU);
        System.out.println("BOT:  " + wayB);
        System.out.println("User: " + wayUser);
        System.out.println("BOT:  " + wayBot);

        model.addAttribute("wayUser", wayUser);
        model.addAttribute("wayBot", wayBot);
        String winner;
        if (wayUser > wayBot) {
            winner = tarakanUser.getTarname();
            tarakanService.updateLevel(tarakanUser, 1);
            tarakanService.updateWin(tarakanUser);
        } else if (wayUser < wayBot) {
            winner = tarakanBot.getTarname();
            tarakanService.updateLoss(tarakanUser);
        } else {
            winner = "Friendship won!!!";
            tarakanService.updateDraw(tarakanUser);
        }
        tarakanService.updateRuning(tarakanUser);
        model.addAttribute("winner", winner);
        model.addAttribute("tarId", tarakanUser.getId());
        model.addAttribute("tarBotId", tarakanBot.getId());
        return "tar";
    }

    @GetMapping("/run_tarakan_six")
    public String runTarakanSix(@RequestParam Long tarId, @RequestParam int numOfTarakans, Model model) {
        Tarakan tarakanUser = tarakanService.getTarakanById(tarId);
        ArrayList<Tarakan> tarakanBots = (ArrayList<Tarakan>) tarakanService.generateTarakanBotsByUserLevel(tarId, numOfTarakans);

        ArrayList<Integer> numOfBotsByUserLevel = (ArrayList<Integer>) tarakanService.numOfBotsByUserLevel(tarakanUser.getLevel());
        model.addAttribute("numOfBotsByUserLevel",numOfBotsByUserLevel);

        String[] names = {"Oggi", "Jeck", "Joi", "Di-di", "Marki", "Iggo", "Lyolik", "Josh", "Do-do", "Dodik"};
        Random rnd = new Random();
        List<String> freeNames = null;
        List<String> uniqNames = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            if (freeNames == null || freeNames.size() == 0) {
                freeNames = new ArrayList<>(Arrays.asList(names));
            }
            String tName = freeNames.remove(rnd.nextInt(freeNames.size()));
            uniqNames.add(i, tName);
        }
        for (int i = 0; i < numOfTarakans; i++) {
            tarakanBots.get(i).setTarname(uniqNames.get(i) + " " + tarakanBots.get(i).getTarname());
        }
        String tarName = tarakanUser.getTarname();

        model.addAttribute("tarakanName", tarName);
        model.addAttribute("tarakanBots", tarakanBots);

        Random random = new Random();
        int wayUser = 0;
        int stepUser;
        List<Integer> stepsBot = new ArrayList<>();
        List<Integer> wayBots = new ArrayList<>();
        for (int i = 0; i < numOfTarakans; i++) {
            stepsBot.add(0);
            wayBots.add(0);
        }
        List<Integer> wayU = new ArrayList<>();
        List<Integer> wayB = new ArrayList<>();
        boolean isFinish = true;
        while (isFinish) {
            stepUser = random.nextInt(tarakanUser.getStep() + 1);
            wayUser = wayUser + stepUser;
            for (int i = 0; i < numOfTarakans; i++) {
                stepsBot.set(i, random.nextInt(tarakanBots.get(i).getStep() + 1));
                wayBots.set(i, wayBots.get(i) + stepsBot.get(i));
                tarakanBots.get(i).setWayForBot(wayBots.get(i));
            }

            wayU.add(wayUser);

            for (int i = 0; i < numOfTarakans; i++) {
                if (wayUser >= 100 || wayBots.get(i) >= 100) {
                    isFinish = false;
                }
            }
        }
        /**
         * rouding max way of tarakan
         * */
        if (wayUser > 100) {
            wayUser = 100;
        }
        for (int i = 0; i < numOfTarakans; i++) {
            if (wayBots.get(i) > 100) {
                wayBots.set(i, 100);
            }
        }

        System.out.println("User: " + wayU);
        System.out.println("BOT:  " + wayB);
        System.out.println("User: " + wayUser);

        model.addAttribute("wayUser", wayUser);
        model.addAttribute("wayBots", wayBots);
        /**
         * Sorting tarakans
         * **/
        HashMap<String, Integer> map = new HashMap<>();
        map.put(tarakanUser.getTarname(), wayUser);
        for (int i = 0; i < numOfTarakans; i++) {
            map.put(tarakanBots.get(i).getTarname(), wayBots.get(i));
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        System.out.println(sortedMap);
        System.out.println(sortedMap.values());
        model.addAttribute("place", sortedMap);

        List<String> winnerList = new ArrayList<>(sortedMap.keySet());

        /**
         * WINNER
         * */
        String message = "";
        int place = winnerList.indexOf(tarakanUser.getTarname()) + 1;
        if (numOfTarakans == 1 && wayUser == wayBots.get(0)) {
            message = "Friendship won!!!";
            tarakanService.updateDraw(tarakanUser);
        } else if (wayUser >= 100) {
            tarakanService.updateLevel(tarakanUser, (6 - tarakanUser.getLevel()));
            tarakanService.updateWin(tarakanUser);
            message = "Ви отримуєте " + (6 - tarakanUser.getLevel()) + " досвіду, ваш досвід: " + tarakanUser.getExperience() + ", ваш Level: " + tarakanUser.getLevel();
        } else if (wayUser < 100 && place <= 3) {
            tarakanService.updateLoss(tarakanUser);
            message = "Ви зайняли " + place + "-е місце і отримуєте 0 досвіду, ваш досвід: " + tarakanUser.getExperience() + ", ваш Level: " + tarakanUser.getLevel();
        } else if (wayUser < 100 && place > 3) {
            tarakanService.updateLoss(tarakanUser);
            tarakanService.updateLevel(tarakanUser, Math.negateExact(place));
            message = "Ви зайняли " + place + "-е місце і втрачаєте " + (place) + " досвіду, ваш досвід: " + tarakanUser.getExperience() + ", ваш Level: " + tarakanUser.getLevel();

        }
        tarakanService.updateRuning(tarakanUser);
        model.addAttribute("message", message);
        model.addAttribute("winner", winnerList.get(0));
        model.addAttribute("tarId", tarakanUser.getId());

        return "tar_six";
    }

    @GetMapping("/reload")
    public String reload() {
        return "redirect:/tar";
    }

    @GetMapping("/back_to_list")
    public String backToList() {
        return "redirect:/tarlist";
    }
}
