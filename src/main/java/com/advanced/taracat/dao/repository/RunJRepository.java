package com.advanced.taracat.dao.repository;

import com.advanced.taracat.dao.entity.RunJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunJRepository extends JpaRepository<RunJ, Long> {
    List<RunJ> findAllByUser_Id(Long id);
    List<RunJ> findAllByUser_Username(String username);
}
