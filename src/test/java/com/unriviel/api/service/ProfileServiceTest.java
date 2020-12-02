//package com.unriviel.api.service;
//
//import com.unriviel.api.dto.Response;
//import com.unriviel.api.exception.UseNotfoundException;
//import com.unriviel.api.model.Profile;
//import com.unriviel.api.model.RelevantQsAns;
//import com.unriviel.api.model.RelevantQuestion;
//import com.unriviel.api.repository.RelevantQuestionsRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//@SpringBootTest
//class ProfileServiceTest {
//
//    @Autowired
//    ProfileService profileService;
//    @Autowired
//    RelevantQuestionsRepository relevantQuestionsRepository;
//    @Test
//    void create() {
//    }
//
//    @Test
//    void findByUseName() {
//    }
//
//    @Test
//    void save() throws UseNotfoundException {
//        Profile profile = new Profile();
//        profile.setProfileImageUrl("http://lochalhost:9004/api/image/{id}");
//        RelevantQuestion question = relevantQuestionsRepository.getOne(1L);
//        List<RelevantQsAns> list = new ArrayList<>();
//        RelevantQsAns ans = new RelevantQsAns();
//        ans.setQuestion(question);
//        ans.setAnswer("that my ans");
//        list.add(ans);
//        profile.setRelevantQsAnsList(list);
//        profile.addSocialMediaLinks(List.of("sosial medial link "));
//        Response response = profileService.create(profile, "admin@gmail.com");
//        assertEquals(201,response.getStatusCode());
//
//    }
//    @Test
//    void update(){
//
//        Profile profile = new Profile();
//        profile.setProfileImageUrl("http://lochalhost:9004/api/image/{id}");
//        RelevantQuestion question = relevantQuestionsRepository.getOne(1L);
//        List<RelevantQsAns> list = new ArrayList<>();
//        RelevantQsAns ans = new RelevantQsAns();
//        ans.setQuestion(question);
//        ans.setAnswer("that my ans");
//        list.add(ans);
//        profile.setRelevantQsAnsList(list);
//        profile.addSocialMediaLinks(List.of("sosial medial link "));
//        Response response = profileService.create(profile, "admin@gmail.com");
//        assertEquals(201,response.getStatusCode());
//
//        Profile profile1 = new Profile();
//        profile.setProfileImageUrl("http://lochalhost:9004/api/image/{id}");
//        RelevantQuestion question1 = relevantQuestionsRepository.getOne(1L);
//        List<RelevantQsAns> list1 = new ArrayList<>();
//        RelevantQsAns ans1 = new RelevantQsAns();
//        ans.setQuestion(question1);
//        ans.setAnswer("that my ans update ");
//        list.add(ans1);
//        profile.setRelevantQsAnsList(list1);
//        profile.addSocialMediaLinks(List.of("sosial medial link ","sosial medial link update  "));
//        Response response1 = profileService.update(profile1, "admin@gmail.com");
//        assertEquals(200,response1.getStatusCode());
//    }
//}