package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.DmoOnboarding;
@Repository
public interface DmoOnboardingRepo extends JpaRepository<DmoOnboarding, Long>{

	DmoOnboarding findByVpa(String vpa);

}
