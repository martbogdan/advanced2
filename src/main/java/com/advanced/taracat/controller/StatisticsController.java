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

import java.util.ArrayList;
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
        model.addAttribute("tarakans",allTarakanList);

        List<Cat> allCatsList = catService.getAll();
        Collections.sort(allCatsList, new CatComparator());
        model.addAttribute("allCatsList", allCatsList);
        return "statistic";
    }

    @GetMapping("/list_of_first_10")
    public String listOfFirst10 (Model model){
        List<Tarakan> allTarakanList = tarakanService.getAll();
        Collections.sort(allTarakanList, new TarakanComparator());
        ArrayList<Tarakan> firstTenTarakans= new ArrayList<>();
        if (allTarakanList.size()<=10){
            firstTenTarakans= (ArrayList<Tarakan>) allTarakanList;
        } else {
            for (int i=0; i<10; i++){
                firstTenTarakans.add(i,allTarakanList.get(i));
            }
        }

        model.addAttribute("tarakans",firstTenTarakans);

        List<Cat> allCatsList = catService.getAll();
        Collections.sort(allCatsList, new CatComparator());
        model.addAttribute("allCatsList", allCatsList);
        return "statistic";
    }
}
