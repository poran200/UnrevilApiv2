//package com.unriviel.api.util;
//
//
//import com.unriviel.api.model.RelevantQuestion;
//import com.unriviel.api.model.Role;
//import com.unriviel.api.model.RoleName;
//import com.unriviel.api.repository.RelevantQuestionsRepository;
//import com.unriviel.api.repository.UserRepository;
//import com.unriviel.api.service.impl.RoleService;
//import com.unriviel.api.service.impl.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//class DbInit {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RelevantQuestionsRepository questionsRepository;
//    @Autowired
//    RoleService roleService;
//    @PostConstruct
//    private void postConstruct() {
//        roleService.create(new Role(RoleName.ROLE_ADMIN));
//        Role role = roleService.create(new Role(RoleName.ROLE_USER));
////        UserRegDto regDto= new UserRegDto();
////        regDto.setEmail("admin@gamil.com");
////        regDto.setUserName("admin");
////        regDto.setFullName("admin user");
////        regDto.setPassword("admin");
////        User user = userService.createUser(regDto, RoleName.ROLE_ADMIN);
////        user.addRole(role);
////        userRepository.save(user);
//        questionsRepository.save(new RelevantQuestion("Which destination do you have content for?"));
//        questionsRepository.save(new RelevantQuestion("Which destination do you have radar?"));
//        questionsRepository.save(new RelevantQuestion("Describe yur style"));
//    }
//}
