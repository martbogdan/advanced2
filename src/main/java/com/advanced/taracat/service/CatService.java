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

    public List<Cat> getAll (){
        return catRepository.findAll();
    }
    public List<Cat> getAllByUserId (Long id){
        return catRepository.findAllByUserId(id);
    }
    public List<Cat> getAllByUsername (String username) {
        return catRepository.findAllByUser_Username(username);
    }
    public Cat getCatById(Long id){
        return catRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    public Cat create (Cat cat){
        return catRepository.save(cat);
    }
    public void delete (Long id){
        Optional<Cat> toDelete = catRepository.findById(id);
        if (toDelete.isPresent()){
            catRepository.delete(toDelete.get());
        }
    }
}
