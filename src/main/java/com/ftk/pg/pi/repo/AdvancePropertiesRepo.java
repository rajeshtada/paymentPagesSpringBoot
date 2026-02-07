package com.ftk.pg.pi.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftk.pg.pi.modal.AdvanceProperties;

@Repository
@Transactional(readOnly = true)
public interface AdvancePropertiesRepo extends JpaRepository<AdvanceProperties, Long>{

	List<AdvanceProperties> findByPropertyKeyLike(String string);

	AdvanceProperties findByPropertyKey(String string);

}
