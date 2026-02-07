package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.SMS;

@Repository
public interface SMSRepo extends JpaRepository<SMS, Long>{

}
