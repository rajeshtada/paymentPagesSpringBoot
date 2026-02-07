package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.Bank;
@Repository
public interface BankRepo extends JpaRepository<Bank, Long>{

//	List<Bank> findByStatusAndId(boolean b, Long[] bankIdArray);
	List<Bank> findByStatusAndIdIn(boolean b, List<Long> bankIdList);

	List<Bank> findByTpvEnable(boolean tpvEnable);


}
