package com.revature.ers_backend.controllers;

import com.revature.ers_backend.exceptions.NoUsersFoundException;
import com.revature.ers_backend.models.Role;
import com.revature.ers_backend.models.User;
import com.revature.ers_backend.services.UserService;
import jakarta.annotation.security.PermitAll;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new NoUsersFoundException("User with username '" + username + "' not found");
        }
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUserRole(@PathVariable int userId,@RequestBody Role role) {
        return ResponseEntity.status(200).body(userService.updateUserRole(userId, role));
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable int userId, Principal principal) {
        if(userService.getUserByUsername(principal.getName()).getRole() != Role.MANAGER) {
            return ResponseEntity.status(403).build();
        }
        else {
            return ResponseEntity.status(200).body(userService.deleteUser(userId));
        }
    }

    @PatchMapping("/userId")
    public ResponseEntity<User> updateUser(@RequestBody Role role, @PathVariable int userId,Principal principal) {
        if(userService.getUserByUsername(principal.getName()).getRole() != Role.MANAGER) {
            return ResponseEntity.status(403).build();
        }
        else{
            return ResponseEntity.status(200).body(userService.updateUserRole(userId, role));
        }

    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return ResponseEntity.ok().body(userService.getUserByUsername(principal.getName()));
    }

    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<String> handleNoUsersFoundException(NoUsersFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
