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
import java.util.Optional;

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


//        userRepository.deleteAll();
//        roleRepository.deleteAll();
        if (roleRepository.findByRole(RoleName.ROLE_USER).isEmpty()){
            roleService.create(new Role(RoleName.ROLE_USER));
        }
        if(roleRepository.findByRole(RoleName.ROLE_ADMIN).isEmpty()) {
            roleService.create(new Role(RoleName.ROLE_ADMIN));
        }
        Optional<User> byEmail = userRepository.findByEmail("admin@gmail.com");
        if (byEmail.isEmpty()){
            User user= new User();
            user.setEmail("admin@gmail.com");
            user.setUsername("admin");
            user.setFullName("admin user");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setActive(true);
            user.setEmailVerified(true);
            userService.saveWithRole(user,RoleName.ROLE_ADMIN);
        }
//        questionsRepository.deleteAll();
        String qs1 = "Which destination do you have content for?";
        String qs2 = "Which destination do you have radar?";
        String qs3 = "Describe yur style";
        if (questionsRepository.findByQuestion(qs1).isEmpty()){
            questionsRepository.save(new RelevantQuestion(qs1));
        }
        if (questionsRepository.findByQuestion(qs2).isEmpty()){
            questionsRepository.save(new RelevantQuestion(qs2));
        }
        if (questionsRepository.findByQuestion(qs3).isEmpty()){
            questionsRepository.save(new RelevantQuestion(qs3));
        }

    }
}
