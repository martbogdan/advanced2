package com.advanced.taracat.dao.repository;

import com.advanced.taracat.dao.entity.Tarakan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarakanRepository extends JpaRepository<Tarakan,Long> {
    List<Tarakan> findAllByUser_Username (String username);
    Tarakan findByTarname (String tarname);
}
