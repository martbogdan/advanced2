package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Tarakan;
import com.advanced.taracat.dao.entity.User;
import com.advanced.taracat.dao.repository.TarakanRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class TarakanService {


    @Autowired
    private TarakanRepository tarakanRepository;

    @Autowired
    private UserService userService;

    public List<Tarakan> getAll() {
        return tarakanRepository.findAll();
    }

    public List<Tarakan> getAllByUsername(String username) {
        return tarakanRepository.findAllByUser_Username(username);
    }

    public Tarakan getTarakanById(Long id) {
        return tarakanRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Tarakan getTarakanByName(String name) {
        return tarakanRepository.findByTarname(name);
    }

    public Tarakan create(String tarname, User user) {

        Tarakan tarakan = new Tarakan();

        tarakan.setUser(user);
        tarakan.setTarname(tarname);
        tarakan.setLevel(1);
        tarakan.setExperience(0);
        tarakan.setStep(3);
        return tarakanRepository.save(tarakan);
    }

    public void delete(Long id) {
        Optional<Tarakan> toDelete = tarakanRepository.findById(id);
        if (toDelete.isPresent()) {
            tarakanRepository.delete(toDelete.get());
        }
    }

    public Tarakan update (Tarakan tarakan){
        Tarakan tarakanDB = tarakanRepository.findById(tarakan.getId()).get();
        tarakanDB.setExperience(tarakan.getExperience());
        if (tarakan.getExperience()>5){
            tarakanDB.setLevel(2);
        }
        tarakanRepository.save(tarakanDB);
        return tarakanDB;
    }
}
