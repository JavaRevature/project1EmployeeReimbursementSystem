package com.revature.ers_backend.daos;

import com.revature.ers_backend.models.Reimbursement;
import com.revature.ers_backend.models.Status;
import com.revature.ers_backend.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {

    List<Reimbursement> findBySubmittedBy(User user);

    List<Reimbursement> findByStatusAndSubmittedBy(Status status, User userByUsername);

    List<Reimbursement> findByStatus(Status status);
}
