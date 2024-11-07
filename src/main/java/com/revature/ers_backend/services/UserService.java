package com.revature.ers_backend.services;

import com.revature.ers_backend.daos.UserDAO;
import com.revature.ers_backend.models.Role;
import com.revature.ers_backend.models.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    private JWTService jwtService;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    public User registerUser(User user) {
        if(user.getUsername().isBlank() || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Username and password cannot be blank");
        }
        return userDAO.save(user);
    }

    public User getUserByUsername(String username) {
        if(username.isBlank()){
            throw new IllegalArgumentException("Username cannot be blank");
        }
        return userDAO.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User updateUserRole(int userId, Role role) {

        User user = userDAO.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setRole(role);

        userDAO.save(user);

        return user;
    }

    @PreAuthorize("hasRole('MANAGER')")
    public User deleteUser(int userId) {

        User user = userDAO.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        userDAO.deleteById(userId);

        return user;
    }


    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){
           return jwtService.generateToken(user.getUsername());
        }

        return "failed";
    }
}
