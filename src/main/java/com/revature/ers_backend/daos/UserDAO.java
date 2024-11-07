package com.revature.ers_backend.daos;

import com.revature.ers_backend.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
