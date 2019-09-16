package com.advanced.taracat.controllerview;

import com.advanced.taracat.dao.entity.Tarakan;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.TarakanRepository;
import com.advanced.taracat.dao.repository.UserRepository;
import com.advanced.taracat.service.CatService;
import com.advanced.taracat.service.TarakanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GameController {
    @Autowired
    private CatService catService;
    @Autowired
    private TarakanService tarakanService;
    @Autowired
    private TarakanRepository tarakanRepository;
    UserRepository userRepository;
    AuthenticatedPrincipal authPrincipal;


    @GetMapping("/cat")
    public String catFight(){
        return "cat";
    }
    @GetMapping("/tar")
    public String runTarakan(){
        return "tar";
    }
    @GetMapping("/tarlist")
    public String tarList(){return "tarlist";}

    @PostMapping("/tarlist")
    public String addTarakan (){
      Tarakan tarakan = new Tarakan();
        User user = userRepository.findByUsername(authPrincipal.getName());
      tarakan.setUser(user);
//      tarakan.setTarname(tarname);
//      tarakan.setLevel(0);
//      tarakan.setExperience(0);
//      tarakan.setStep(3);
      tarakanRepository.save(tarakan);
        return "tarlist";
    }
}
