package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("all")
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @GetMapping("user/{name}")
    public User getUserByName(@PathVariable String name){
        return userService.getUserByUsername(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveNewUser (@RequestBody User user){
        return userService.create(user);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser (@PathVariable Long id, @RequestBody User user){
        User userDB = userService.getUserById(id);
        userDB.setUsername(user.getUsername());
        userDB.setPassword(user.getPassword());
        userService.create(userDB);
        return userDB;
    }

    @DeleteMapping("{id}")
    public User delete(@PathVariable Long id){
        User deletedUser = userService.getUserById(id);
        if (deletedUser != null){
            userService.delete(id);
        }
        return deletedUser;
    }
}
