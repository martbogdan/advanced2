package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Role;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.UserRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUsername(userName);
    }
    public User getUserByNameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User getUserById (Long id){
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User createUpdate (User user){
        User userSave = user.getId() == null ? create(user) : updateUser(user);
        return userRepository.save(userSave);
    }

    public User create(User user){
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User updateUser(User user){
        User userDB = userRepository.findById(user.getId()).get();
        if (!StringUtils.isEmpty(user.getUsername())){
            userDB.setUsername(user.getUsername());
        }
        if (!StringUtils.isEmpty(user.getPassword())){
            userDB.setPassword(user.getPassword());
        }
        if (!StringUtils.isEmpty(user.getRoles())){
            userDB.setRoles(user.getRoles());
        }
        if (!StringUtils.isEmpty(user.getFights())){
            userDB.setFights(user.getFights());
        }
        if (!StringUtils.isEmpty(user.getWin())){
            userDB.setWin(user.getWin());
        }
        if (!StringUtils.isEmpty(user.getLoss())){
            userDB.setLoss(user.getLoss());
        }
        return userDB;
    }

    public void delete(Long id){
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isPresent()){
            userRepository.delete(toDelete.get());
        }
     }
}
