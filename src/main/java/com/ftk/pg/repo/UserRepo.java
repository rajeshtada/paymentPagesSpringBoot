package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsernameAndEnabled(String login, boolean b);
	

	List<User> findByMidAndRoleId(Long mid, long l);

	User findByUsername(String userName);


//	User findByUsername(User user);

}
