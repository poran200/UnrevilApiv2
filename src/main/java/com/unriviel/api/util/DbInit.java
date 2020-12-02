package com.unriviel.api.util;


import com.unriviel.api.model.RelevantQuestion;
import com.unriviel.api.model.Role;
import com.unriviel.api.model.RoleName;
import com.unriviel.api.model.User;
import com.unriviel.api.repository.RelevantQuestionsRepository;
import com.unriviel.api.repository.RoleRepository;
import com.unriviel.api.repository.UserRepository;
import com.unriviel.api.service.impl.RoleService;
import com.unriviel.api.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class DbInit {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RelevantQuestionsRepository questionsRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    private void postConstruct() {


        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleService.create(new Role(RoleName.ROLE_ADMIN));
        roleService.create(new Role(RoleName.ROLE_USER));
        User user= new User();
        user.setEmail("admin@gmail.com");
        user.setUsername("admin");
        user.setFullName("admin user");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setActive(true);
         user.setEmailVerified(true);
         userService.saveWithRole(user,RoleName.ROLE_ADMIN);
        questionsRepository.save(new RelevantQuestion("Which destination do you have content for?"));
        questionsRepository.save(new RelevantQuestion("Which destination do you have radar?"));
        questionsRepository.save(new RelevantQuestion("Describe yur style"));
    }
}
