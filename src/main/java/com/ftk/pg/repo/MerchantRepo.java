package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.Merchant;

@Repository
public interface MerchantRepo extends JpaRepository<Merchant, Long> {

	Merchant findByMid(Long mid);

	Merchant findByMidAndStatus(Long mid, int status);

	

}
