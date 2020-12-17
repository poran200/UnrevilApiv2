package com.unriviel.api.service;

import com.unriviel.api.repository.RoleRepository;
import com.unriviel.api.repository.UserPaginationRepertory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserPaginationRepertory userRepository;
    @Autowired
    RoleRepository roleRepository;


    @Test
    void findUserIsAdmin() {
//        var streamable = userRepository.findAll(PageRequest.of(0, 10)).filter(User::isAdmin);
//        System.out.println("collect = " + streamable.toList());
//        var userPage = userRepository.findAllByUsernameStartingWithAndRolesRole("a",RoleName.ROLE_ADMIN , Pageable.unpaged());
//        System.out.println("userPage = " + userPage.getContent().toString());
    }
}
