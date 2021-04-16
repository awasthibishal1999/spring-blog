package com.awasthi.bishal.Springblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awasthi.bishal.Springblog.model.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String username);

}
