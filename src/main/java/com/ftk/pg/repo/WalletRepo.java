package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.Wallet;
@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long>{

}
