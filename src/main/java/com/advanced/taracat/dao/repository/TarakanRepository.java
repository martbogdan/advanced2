package com.advanced.taracat.dao.repository;

import com.advanced.taracat.dao.entity.Tarakan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarakanRepository extends JpaRepository<Tarakan,Long> {
}
