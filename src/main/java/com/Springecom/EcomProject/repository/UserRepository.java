package com.Springecom.EcomProject.repository;

import com.Springecom.EcomProject.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import java.lang.ScopedValue;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByusername(String username);

    Boolean existsByEmail(String email);


}
