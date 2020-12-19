//package com.unriviel.api.service;
//
//import com.unriviel.api.enums.ReviewStatus;
//import com.unriviel.api.model.Profile;
//import com.unriviel.api.model.RoleName;
//import com.unriviel.api.model.metadata.VideoMetaData;
//import com.unriviel.api.repository.ProfileRepository;
//import com.unriviel.api.repository.VideoMetaDataRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//
//@SpringBootTest
//public class UserRepositoryTest {
////    @Autowired
////    UserPaginationRepertory userRepository;
////    @Autowired
////    RoleRepository roleRepository;
////    @Autowired
////    VideoMetaDataRepository metaDataRepository;
//     @Autowired
//    ProfileRepository profileRepository;
//
//
//    @Test
//
//    void findUserIsAdmin() {
////        var streamable = userRepository.findAll(PageRequest.of(0, 10)).filter(User::isAdmin);
////        System.out.println("collect = " + streamable.toList());
////        var userPage = userRepository.findAllByUsernameStartingWithAndRolesRole("a",RoleName.ROLE_ADMIN , Pageable.unpaged());
////        System.out.println("userPage = " + userPage.getContent().toString());
//
//        var page = profileRepository.findAllByUserRolesRole(RoleName.ROLE_REVIEWER, Pageable.unpaged());
//        System.out.println("page = " + page.getContent().toString());
//
//        Calendar cal = new GregorianCalendar();
//        cal.add(Calendar.DAY_OF_MONTH, -7);
//        Instant sevenDaysAgo = cal.getTime().toInstant();
//        System.out.println("sevenDaysAgo = " + sevenDaysAgo);
//
//
//    }
//}
