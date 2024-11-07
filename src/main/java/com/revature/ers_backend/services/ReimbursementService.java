package com.revature.ers_backend.services;

import com.revature.ers_backend.daos.ReimbursementDAO;
import com.revature.ers_backend.models.DTOs.UserReimburseDTO;
import com.revature.ers_backend.models.Reimbursement;
import com.revature.ers_backend.models.Status;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReimbursementService {
    @Autowired
    private UserService userService;

    @Autowired
    private ReimbursementDAO reimbursementDAO;

    public Reimbursement createReimbursement(UserReimburseDTO userReimburseDTO, Principal principal) {
        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setAmount(userReimburseDTO.getAmount());
        reimbursement.setDescription(userReimburseDTO.getDescription());
        reimbursement.setStatus(Status.PENDING);
        reimbursement.setSubmittedBy(userService.getUserByUsername(principal.getName()));
        return reimbursementDAO.save(reimbursement);
    }

    public List<Reimbursement> getUsersReimbursements(Principal principals) {
        return reimbursementDAO.findBySubmittedBy(userService.getUserByUsername(principals.getName()));
    }

    public List<Reimbursement> getAllReimbursements() {
        return reimbursementDAO.findAll();
    }

    public List<Reimbursement> getUsersPendingReimbursements(Principal principal) {
        return reimbursementDAO.findByStatusAndSubmittedBy(Status.PENDING, userService.getUserByUsername(principal.getName()));
    }

    public Reimbursement updateReimbursementStatus(int reimbursementId, Status status) {
        boolean present = reimbursementDAO.findById(reimbursementId).isPresent();
        if(present) {
            Reimbursement reimbursement = reimbursementDAO.findById(reimbursementId).get();
            reimbursement.setStatus(status);
            return reimbursementDAO.save(reimbursement);
        }
        else{
            return null;
        }
    }
}
