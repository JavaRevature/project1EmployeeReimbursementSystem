package com.revature.ers_backend.controllers;

import com.revature.ers_backend.exceptions.DuplicatedUsersException;
import com.revature.ers_backend.exceptions.NoUsersFoundException;
import com.revature.ers_backend.models.DTOs.LoginResponseDTO;
import com.revature.ers_backend.models.User;
import com.revature.ers_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public LoginResponseDTO registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);

        if(newUser != null && !userService.verify(user).equals("failed")) {
            User verifiedUser = userService.getUserByUsername(user.getUsername());
            return new LoginResponseDTO(userService.verify(user), verifiedUser.getUsername(), verifiedUser.getRole());
        }
        else{
            return null;
        }
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody User user) {
        if(!userService.verify(user).equals("failed")) {
            User verifiedUser = userService.getUserByUsername(user.getUsername());
            return new LoginResponseDTO(userService.verify(user), verifiedUser.getUsername(), verifiedUser.getRole());
        }
        else{
            return null;
        }
    }


    @ExceptionHandler(DuplicatedUsersException.class)
    public ResponseEntity<String> handleNoUsersFoundException(DuplicatedUsersException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    
}
