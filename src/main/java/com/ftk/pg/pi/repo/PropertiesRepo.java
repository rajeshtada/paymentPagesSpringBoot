package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.Properties;
@Repository("PortalPropertiesRepo")
public interface PropertiesRepo extends JpaRepository<Properties, Long>{

	Properties findByMidAndStatus(Long mid, boolean b);

}
