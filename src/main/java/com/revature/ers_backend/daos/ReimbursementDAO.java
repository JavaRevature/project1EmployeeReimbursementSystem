package com.revature.ers_backend.daos;

import com.revature.ers_backend.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {

}
