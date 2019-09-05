package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.UserRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName);
    }

    public User getUserById (Long id){
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isPresent()){
            userRepository.delete(toDelete.get());
        }
     }
}
