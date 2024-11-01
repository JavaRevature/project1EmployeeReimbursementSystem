package com.revature.ers_backend.daos;

import com.revature.ers_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
