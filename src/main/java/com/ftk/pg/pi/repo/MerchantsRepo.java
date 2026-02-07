package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.Merchants;
@Repository
public interface MerchantsRepo extends JpaRepository<Merchants, Long> {

	Merchants findByMid(Long mid);

}
