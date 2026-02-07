package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.SMSConfiguration;
import com.ftk.pg.modal.Token;

@Repository
public interface SMSConfigurationRepo extends JpaRepository<SMSConfiguration, Long> {

}
