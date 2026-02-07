package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.MerchantCommision;

@Repository
public interface MerchantCommisionRepo extends JpaRepository<MerchantCommision, Long>, JpaSpecificationExecutor<MerchantCommision> {
}