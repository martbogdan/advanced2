package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Tarakan;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.TarakanRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarakanService {


    @Autowired
    private TarakanRepository tarakanRepository;

    @Autowired
    private UserService userService;

    public List<Tarakan> getAll() {
        return tarakanRepository.findAll();
    }

    public List<Tarakan> getAllByUsername(String username) {
        return tarakanRepository.findAllByUser_Username(username);
    }

    public Tarakan getTarakanById(Long id) {
        return tarakanRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Tarakan getTarakanByName(String name) {
        return tarakanRepository.findByTarname(name);
    }

    public Tarakan create(String tarname, User user) {

        Tarakan tarakan = new Tarakan();

        tarakan.setUser(user);
        tarakan.setTarname(tarname);
        tarakan.setLevel(1);
        tarakan.setExperience(0);
        tarakan.setStep(3);
        tarakan.setLoss(0);
        tarakan.setWin(0);
        tarakan.setRunning(0);
        return tarakanRepository.save(tarakan);
    }

    public void delete(Long id) {
        Optional<Tarakan> toDelete = tarakanRepository.findById(id);
        if (toDelete.isPresent()) {
            tarakanRepository.delete(toDelete.get());
        }
    }

    public Tarakan updateLevel (Tarakan tarakan){
        Tarakan tarakanDB = tarakanRepository.findById(tarakan.getId()).get();
        tarakanDB.setExperience(tarakan.getExperience()+1);
        if (tarakan.getExperience()>=100 && tarakan.getExperience()<=199 && tarakan.getLevel()==1){
            tarakanDB.setLevel(tarakan.getLevel()+1);
            tarakanDB.setStep(tarakan.getStep()+1);
        }else
        if (tarakan.getExperience()>=200 && tarakan.getExperience()<=299 && tarakan.getLevel()==2){
            tarakanDB.setLevel(tarakan.getLevel()+1);
            tarakanDB.setStep(tarakan.getStep()+1);
        }else
        if (tarakan.getExperience()>=300 && tarakan.getExperience()<=399 && tarakan.getLevel()==3){
            tarakanDB.setLevel(tarakan.getLevel()+1);
            tarakanDB.setStep(tarakan.getStep()+1);
        }else
        if (tarakan.getExperience()>=400 && tarakan.getExperience()<=499 && tarakan.getLevel()==4){
            tarakanDB.setLevel(tarakan.getLevel()+1);
            tarakanDB.setStep(tarakan.getStep()+1);
        }
        tarakanRepository.save(tarakanDB);
        return tarakanDB;
    }

    public Tarakan updateWin (Tarakan tarakan){
        Tarakan tarakanDB = tarakanRepository.findById(tarakan.getId()).get();
        tarakanDB.setWin(tarakan.getWin()+1);
        tarakanRepository.save(tarakanDB);
        return tarakanDB;
    }

    public Tarakan updateLoss (Tarakan tarakan){
        Tarakan tarakanDB = tarakanRepository.findById(tarakan.getId()).get();
        tarakanDB.setLoss(tarakan.getLoss()+1);
        tarakanRepository.save(tarakanDB);
        return tarakanDB;
    }

    public Tarakan updateDraw (Tarakan tarakan){
        Tarakan tarakanDB = tarakanRepository.findById(tarakan.getId()).get();
        tarakanDB.setDraw(tarakan.getDraw()+1);
        tarakanRepository.save(tarakanDB);
        return tarakanDB;
    }

    public Tarakan updateRuning (Tarakan tarakan){
        Tarakan tarakanDB = tarakanRepository.findById(tarakan.getId()).get();
        tarakanDB.setRunning(tarakan.getDraw()+tarakan.getWin()+tarakan.getLoss());
        tarakanRepository.save(tarakanDB);
        return tarakanDB;
    }

    public Tarakan selectBot (Long id){
        Tarakan tarakanBot = new Tarakan();
        Tarakan tarakanUser = tarakanRepository.findById(id).orElseThrow(NotFoundException::new);
        if (tarakanUser.getLevel()==1){
            tarakanBot = tarakanRepository.findByTarname("bot1");
        } else if (tarakanUser.getLevel()==2){
            tarakanBot = tarakanRepository.findByTarname("bot2");
        } else if (tarakanUser.getLevel()==3){
            tarakanBot = tarakanRepository.findByTarname("bot3");
        } else if (tarakanUser.getLevel()==4){
            tarakanBot = tarakanRepository.findByTarname("bot4");
        } else if (tarakanUser.getLevel()==5){
            tarakanBot = tarakanRepository.findByTarname("bot5");
        }
        return tarakanBot;
    }
}
