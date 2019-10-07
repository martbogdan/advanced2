package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.RunJ;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.RunJRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunJService {
    @Autowired
    RunJRepository runJRepository;
public List<RunJ> getAll(){
    return runJRepository.findAll();
}
public List<RunJ> getAllByUserId (Long id){
    return runJRepository.findAllByUser_Id(id);
}
public List<RunJ> getAllByUserName (String username){
    return runJRepository.findAllByUser_Username(username);
}
public RunJ getRunJById (Long id){
    return runJRepository.findById(id).orElseThrow(NotFoundException::new);
}
public RunJ create (User user){
    RunJ runJ = new RunJ();
    runJ.setUser(user);
    runJ.setQuantityRunning(0);
    runJ.setBotConfiguration("");
    return runJ;
}

}
