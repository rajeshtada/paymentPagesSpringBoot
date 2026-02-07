package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.FormTransaction;
@Repository
public interface FormTransactionRepo extends JpaRepository<FormTransaction, Long>{

}
