package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftk.pg.modal.Email;

public interface EmailRepo extends JpaRepository<Email, Long>{

}
