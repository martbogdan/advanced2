package com.advanced.taracat.service;

import com.advanced.taracat.dao.entity.Tarakan;
import com.advanced.taracat.dao.repository.TarakanRepository;
import com.advanced.taracat.exeptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarakanService {
   private TarakanRepository tarakanRepository;

   public List<Tarakan> getAll(){
       return tarakanRepository.findAll();
   }

    public List<Tarakan> getAllByUsername (String username){
       return tarakanRepository.findAllByUser_Username(username);
    }

    public Tarakan getTarakanById (Long id){
       return tarakanRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    public Tarakan getTarakanByName (String name){
       return tarakanRepository.findByTarname(name);
    }

    public Tarakan create (Tarakan tarakan){
       return tarakanRepository.save(tarakan);
    }

    public void delete (Long id){
        Optional<Tarakan> toDelete = tarakanRepository.findById(id);
        if (toDelete.isPresent()){
            tarakanRepository.delete(toDelete.get());
        }
    }


}
