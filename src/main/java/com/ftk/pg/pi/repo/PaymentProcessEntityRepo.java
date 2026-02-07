package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.PaymentProcessEntity;
@Repository
public interface PaymentProcessEntityRepo extends JpaRepository<PaymentProcessEntity, Long>{

}
