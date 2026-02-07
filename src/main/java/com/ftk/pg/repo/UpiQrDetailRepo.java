package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftk.pg.modal.UpiQrDetail;

public interface UpiQrDetailRepo extends JpaRepository<UpiQrDetail, Long>{

	UpiQrDetail findByMid(Long mid);

	UpiQrDetail findByVpa(String trim);

	UpiQrDetail findByVpaAndEnable(String vpa, boolean b);

	UpiQrDetail findByMidAndEnable(Long merchantId, boolean b);

	UpiQrDetail findFirstByMidAndVpaIsNotNull(Long merchantId);

}
