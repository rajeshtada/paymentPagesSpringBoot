package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.KotakTransactionLog;
@Repository
public interface KotakTransactionLogRepo extends JpaRepository<KotakTransactionLog, Long>{

}
