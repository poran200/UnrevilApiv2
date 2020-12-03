package com.unriviel.api.repository;

import com.unriviel.api.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
      Optional<Profile> findByUserEmail(String userName);
      Optional<Profile> findByIdAndUserEmail(long  id, String userName);
//      Page<Profile> findAllByUserRoles(List<Role> roles, Pageable pageable);
}
