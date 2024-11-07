package com.revature.ers_backend.controllers;

import com.revature.ers_backend.models.DTOs.UserReimburseDTO;
import com.revature.ers_backend.models.Reimbursement;
import com.revature.ers_backend.models.Role;
import com.revature.ers_backend.models.Status;
import com.revature.ers_backend.services.ReimbursementService;
import com.revature.ers_backend.services.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {

    @Autowired
    UserService userSevice;

    @Autowired
    ReimbursementService reimbursementService;

    @PostMapping
    public ResponseEntity<Reimbursement> createUserReimbursements(Principal principal,@RequestBody UserReimburseDTO userReimburseDTO) {
        return ResponseEntity.ok().body(reimbursementService.createReimbursement(userReimburseDTO, principal));
    }

    @GetMapping
    public ResponseEntity<List<Reimbursement>> getUserSelfReimbursements(Principal principal) {
        return ResponseEntity.ok().body(reimbursementService.getUsersReimbursements(principal));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Reimbursement>> getUsersPendingReimbursements(Principal principal) {
        return ResponseEntity.ok().body(reimbursementService.getUsersPendingReimbursements(principal));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reimbursement>> getAllReimbursements(Principal principal) {
        if(userSevice.getUserByUsername(principal.getName()).getRole() != Role.MANAGER) {
            return ResponseEntity.status(403).build();
        }
        else {
            return ResponseEntity.ok().body(reimbursementService.getAllReimbursements());
        }
    }

    @PatchMapping("/{reimbursementId}")
    public ResponseEntity<Reimbursement> updateReimbursementStatus(@PathVariable int reimbursementId, @RequestBody Status status, Principal principal) {
        if(userSevice.getUserByUsername(principal.getName()).getRole() != Role.MANAGER) {
            return ResponseEntity.status(403).build();
        }
        else{
            return ResponseEntity.ok().body(reimbursementService.updateReimbursementStatus(reimbursementId, status));
        }
    }



}
