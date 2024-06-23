package com.scm.repo;

import com.scm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Sign_upRepo extends JpaRepository<User,String> {


    Optional<User> findByEmail(String email);


}
