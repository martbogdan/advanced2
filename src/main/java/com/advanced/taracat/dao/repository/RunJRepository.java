package com.advanced.taracat.dao.repository;

import com.advanced.taracat.dao.entity.RunJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunJRepository extends JpaRepository<RunJ, Long> {
}
