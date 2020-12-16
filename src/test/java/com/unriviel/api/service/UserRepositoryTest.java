package com.unriviel.api.service;

import com.unriviel.api.repository.RoleRepository;
import com.unriviel.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

//
//    @Test
//    void findUserIsAdmin() {
//        var streamable = userRepository.findAll(PageRequest.of(0, 10)).filter(User::isAdmin);
//        System.out.println("collect = " + streamable.toList());
//    }
}
