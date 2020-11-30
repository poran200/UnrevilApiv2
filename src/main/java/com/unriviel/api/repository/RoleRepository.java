package com.unriviel.api.repository;

import com.unriviel.api.model.Role;
import com.unriviel.api.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
     Optional<Role> findByRole(RoleName roleName);

}
