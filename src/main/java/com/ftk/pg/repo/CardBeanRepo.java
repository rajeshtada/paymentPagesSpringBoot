package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.CardBean;

@Repository
public interface CardBeanRepo extends JpaRepository<CardBean, Long>{
	
	@Query(value = "SELECT * FROM card_bean WHERE from_bin <= :value AND to_bin >= :value ORDER BY id DESC", nativeQuery = true)
    List<CardBean> getBeanDetailbyBeanValue(@Param("value") int value);

}
