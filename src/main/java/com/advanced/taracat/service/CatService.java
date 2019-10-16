package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Cat;
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

    public List<Cat> getAll(){
        return catRepository.findAll();
    }

    public List<Cat> getAllByUsername (String username){
        return catRepository.findAllByUser_Username(username);
    }

    public Cat getCatById (Long id){
        return catRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    public Cat getCatByName (String name){
        return catRepository.findCatByName(name);
    }

    public Cat create (Cat tarakan){
        return catRepository.save(tarakan);
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
