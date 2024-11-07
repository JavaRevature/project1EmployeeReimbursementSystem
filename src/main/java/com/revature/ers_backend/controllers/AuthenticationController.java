package com.revature.ers_backend.controllers;

import com.revature.ers_backend.exceptions.DuplicatedUsersException;
import com.revature.ers_backend.exceptions.NoUsersFoundException;
import com.revature.ers_backend.models.User;
import com.revature.ers_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.registerUser(user));
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return userService.verify(user);
    }

    @ExceptionHandler(DuplicatedUsersException.class)
    public ResponseEntity<String> handleNoUsersFoundException(DuplicatedUsersException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    
}
