package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.Properties;


import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface PropertiesRepo extends JpaRepository<Properties, Long> {

	Properties findByPropertykey(String keyString);

	List<Properties> findByPropertykeyLike(String string);

}
