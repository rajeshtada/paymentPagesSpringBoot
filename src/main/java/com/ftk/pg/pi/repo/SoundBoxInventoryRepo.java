package com.ftk.pg.pi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.SoundBoxInventory;
@Repository
public interface SoundBoxInventoryRepo extends JpaRepository<SoundBoxInventory, Long>{

	List<SoundBoxInventory> findByVpa(String terminalId);

}
