package com.advanced.taracat.dao.repository;

import com.advanced.taracat.dao.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findAllByUserId (Long id);
    List<Cat> findAllByUser_Username (String username);
}
