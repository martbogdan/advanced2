package com.advanced.taracat.dao.repository;

import com.advanced.taracat.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName (String username);
    User findByUserNameAndPassword (String username, String password);
}
