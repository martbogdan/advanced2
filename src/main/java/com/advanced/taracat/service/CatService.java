package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Cat;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.CatRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatService catService;
    @Autowired
    private UserService userService;

    // Якась хуйня
    public List<Cat> getAll(){
        return catRepository.findAll();
    }

    // Якась хуйня
    public List<Cat> getAllByUsername (String username){
        return catRepository.findAllByUser_Username(username);
    }

    // Якась хуйня
    public Cat getCatById (Long id){
        return catRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    // Якась хуйня
    public Cat getCatByName (String name){
        return catRepository.findCatByName(name);
    }

    public Cat create (Cat tarakan){
        return catRepository.save(tarakan);
    } // Хз шо, треба перепровірити і видалити

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    public void addCat (String catName, String userName){

        User currentUser = userService.getUserByUsername(userName);

        Cat cat = new Cat();
        Cat catDB;

        String catError;

        List<Cat> catCount = catRepository.findAllByUser_Username(currentUser.getUsername());
        int catCountSize = catCount.size();
        if (catCountSize >= 5) {
            catError = "У вас вже існує 5 котів, видаліть одного або декілька котів та добавте нового";
        } else {
            catDB = catRepository.findCatByName(catName);

            if (catDB != null) {
                catError = "Кіт з таким іменем вже існує";
            } else {
                cat.setUser(currentUser);
                cat.setName(catName);
                cat.setCat_level(1);
                cat.setCat_expirience(0);
                cat.setCat_maxexpirience(1000);
                catRepository.save(cat);
                catError = "";
            }
        }

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
