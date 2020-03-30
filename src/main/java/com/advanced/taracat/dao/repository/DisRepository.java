package com.advanced.taracat.dao.repository;

import com.advanced.taracat.dao.entity.Hero;
import com.advanced.taracat.dao.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisRepository extends JpaRepository<Hero, Long> {
    List<Hero> findAllByUser_Username (String username);
    Hero findHeroByName (String heroname);
    /*List<Location> findAl (String username);*/
}