package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Cat;
import com.advanced.taracat.dao.entity.CatBot;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private UserService userService;

    public List<Cat> getAll () {
        return catRepository.findAll();
    }

    // Витяг всіх котів юзера
    public List<Cat> getAllByUsername(String userName){

        return catRepository.findAllByUser_Username(userName);
    }

    // Знайти кота за іменем
    public Cat getCatByName (String catName){
        return catRepository.findCatByName(catName);
    }

    // Знайти всіх котів за Id
    public Cat getCatById (Long id){
        return catRepository.findCatById(id);
    }

    // Генерація бота для битви
    public CatBot catBotGeneration (int catBotLevel, int catBotLast_Hp, CatBot catBot){

        catBot.setName("Кіт "+catBotLevel+" рівня");
        catBot.setLevel(catBotLevel);
        catBot.setHp(catBot.getLevel()*10);
        catBot.setLastHp(catBotLast_Hp*10);

        return catBot;

    }

    public void addCat (String catName, String userName){

        User currentUser = userService.getUserByUsername(userName);

        Cat cat = new Cat();

        cat.setUser(currentUser);
        cat.setName(catName);
        cat.setCat_level(1);
        cat.setCat_expirience(0);
        cat.setCat_maxexpirience(1000);
        catRepository.save(cat);

    }

    public Cat updateExpirience (Cat cat){

        Cat catDB = catRepository.findById(cat.getId()).get();

        int newExp = cat.getCat_expirience();
        int lastExp = cat.getCat_maxexpirience();
        int catLevel = cat.getCat_level();

        if (newExp >= lastExp) {
            cat.setCat_expirience(0);
            cat.setCat_level(catLevel+1);
        } else {
            cat.setCat_expirience(newExp);
            cat.setCat_level(catLevel);
        }

        catRepository.save(cat);

        return cat;

    }

    public void delete (Long id){
        Optional<Cat> toDelete = catRepository.findById(id);
        if (toDelete.isPresent()){
            catRepository.delete(toDelete.get());
        }
    }


}
