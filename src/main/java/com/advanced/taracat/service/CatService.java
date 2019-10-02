package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Cat;
import com.advanced.taracat.dao.repository.CatRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {
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

    public void updateExpirience (Cat cat){

        Cat catDB = catRepository.findById(cat.getId()).get();


        int lastExp = catDB.getCat_expirience();

        /*if (lastExp < (lastExp + cat.getCat_expirience())) {

        } else if ((getCatById(id).getCat_expirience() + catExpirience) >= lastExp) {

        }*/
    }

    public void delete (Long id){
        Optional<Cat> toDelete = catRepository.findById(id);
        if (toDelete.isPresent()){
            catRepository.delete(toDelete.get());
        }
    }


}
