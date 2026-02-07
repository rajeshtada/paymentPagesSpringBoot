package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftk.pg.modal.DmoOnboarding;

public interface DmoOnboardingDao extends JpaRepository<DmoOnboarding, Long> {

	DmoOnboarding findByVpa(String vpa); 
}
