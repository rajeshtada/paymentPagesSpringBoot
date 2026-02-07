package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.DynamicQrRequestModel;
@Repository
public interface DynamicQrRequestModelRepo extends JpaRepository<DynamicQrRequestModel, Long>{

}
