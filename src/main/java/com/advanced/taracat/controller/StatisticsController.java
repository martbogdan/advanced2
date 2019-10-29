package com.advanced.taracat.controller;

import com.advanced.taracat.comparing.CatComparator;
import com.advanced.taracat.comparing.TarakanComparator;
import com.advanced.taracat.dao.entity.Cat;
import com.advanced.taracat.dao.entity.Tarakan;
import com.advanced.taracat.service.CatService;
import com.advanced.taracat.service.TarakanService;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class StatisticsController {
    @Autowired
    TarakanService tarakanService;
    @Autowired
    CatService catService;
    @Autowired
    UserService userService;

    @GetMapping("/list_of_all")
    public String listOfAll (Model model){
        List<Tarakan> allTarakanList = tarakanService.getAll();
        Collections.sort(allTarakanList, new TarakanComparator());
        model.addAttribute("allTarakanList",allTarakanList);

        List<Cat> allCatsList = catService.getAll();
        Collections.sort(allCatsList, new CatComparator());
        model.addAttribute("allCatsList", allCatsList);
        return "statistic";
    }
}
