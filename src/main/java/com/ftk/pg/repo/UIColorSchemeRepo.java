package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.UIColorScheme;
@Repository
public interface UIColorSchemeRepo extends JpaRepository<UIColorScheme, Long>{

	UIColorScheme findByVpa(String vpa);

	UIColorScheme findByPartnerId(Long partnerId);

}
