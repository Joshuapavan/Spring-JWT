package com.joshuapavan.jwtpractice.repos;

import com.joshuapavan.jwtpractice.entities.User;
import com.joshuapavan.jwtpractice.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByRole(Role role);

}
