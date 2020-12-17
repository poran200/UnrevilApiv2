package com.unriviel.api.repository;

import com.unriviel.api.model.RoleName;
import com.unriviel.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaginationRepertory extends PagingAndSortingRepository<User,Long> {
    Page<User> findAllByUsernameStartingWith(String username, Pageable pageable);
    Page<User>findAllByRolesRoleOrUsernameStartingWithOrEmailStartingWithOrFullNameStartingWith(RoleName role, String username, String email, String fullName, Pageable pageable);
    Page<User>findAllByRolesRole(RoleName roles_role, Pageable pageable);
}
