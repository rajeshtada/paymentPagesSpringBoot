package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftk.pg.pi.modal.VPAQrCode;

public interface VPAQrCodeRepo extends JpaRepository <VPAQrCode, Long>{

	VPAQrCode findByMidAndVpa(Long mid, String vpa);

}
