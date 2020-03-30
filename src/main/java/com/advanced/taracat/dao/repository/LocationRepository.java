package com.advanced.taracat.dao.repository;

import com.advanced.taracat.dao.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByLocationOnOff (int locationOnOff);
}
