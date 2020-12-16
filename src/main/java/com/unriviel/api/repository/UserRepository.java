package com.unriviel.api.repository;

import com.unriviel.api.model.Role;
import com.unriviel.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String emailOrUseName,String emailOrUserName);

    Boolean existsByUsername(String username);
    Page<User> findByRoles(Role role, Pageable pageable);
    Page<User>findAllByUsernameStartingWith(String username, Pageable pageable);
}
