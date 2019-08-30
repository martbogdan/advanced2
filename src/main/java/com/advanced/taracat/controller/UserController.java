package com.advanced.taracat.controller;

import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.UserRepository;
import com.advanced.taracat.exeptions.NotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("all")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundExeption::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveNewUser (@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser (@PathVariable Long id, @RequestBody User user){
        User userDB = userRepository.findById(id).orElseThrow(NotFoundExeption::new);
        userDB.setUserName(user.getUserName());
        userDB.setPassword(user.getPassword());
        userDB.setEmail(user.getEmail());
        userDB.setDateOfBirth(user.getDateOfBirth());
        userRepository.save(userDB);
        return userDB;
    }

    @DeleteMapping("{id}")
    public User delete(@PathVariable Long id){
        User deletedUser = userRepository.findById(id).orElse(null);
        if (deletedUser != null){
            userRepository.delete(deletedUser);
        }
        return deletedUser;
    }
}
