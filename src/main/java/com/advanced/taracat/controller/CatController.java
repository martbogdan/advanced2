package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.Cat;
import com.advanced.taracat.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cat")
public class CatController {
    @Autowired
    private CatService catService;

    @GetMapping("all")
    public List<Cat> getAll (){
        return catService.getAll();
    }
    @GetMapping("all/user/{id}")
    public List<Cat> getAllByUserId(@PathVariable Long id){
        return catService.getAllByUserId(id);
    }
    @GetMapping("all/user/{username}")
    public List<Cat> getAllByUsername(@PathVariable String username){
        return catService.getAllByUsername(username);
    }
    @GetMapping("{id}")
    public Cat getById(@PathVariable Long id){
        return catService.getCatById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cat saveNewCat (@RequestBody Cat cat){
        return catService.create(cat);
    }
    @DeleteMapping("{id}")
    public Cat delete(@PathVariable Long id){
        Cat deletedCat = catService.getCatById(id);
        if (deletedCat != null){
            catService.delete(id);
        }
        return deletedCat;
    }
    @PutMapping("{id}")
    public Cat updateCat(@PathVariable Long id){
        Cat catDb = catService.getCatById(id);
        catService.create(catDb);
        return catDb;
    }
}
