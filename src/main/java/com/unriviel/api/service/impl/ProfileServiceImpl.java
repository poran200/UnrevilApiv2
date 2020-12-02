package com.unriviel.api.service.impl;


import com.unriviel.api.dto.ProfileResponseDto;
import com.unriviel.api.dto.Response;
import com.unriviel.api.exception.UseNotfoundException;
import com.unriviel.api.model.Profile;
import com.unriviel.api.model.User;
import com.unriviel.api.repository.ProfileRepository;
import com.unriviel.api.repository.ReleventQuestionAnsRepertory;
import com.unriviel.api.service.ProfileService;
import com.unriviel.api.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ReleventQuestionAnsRepertory releventQuestionAnsRepertory;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ProfileServiceImpl(ProfileRepository profileRepository, ReleventQuestionAnsRepertory releventQuestionAnsRepertory, ModelMapper modelMapper, UserService userService) {
        this.profileRepository = profileRepository;
        this.releventQuestionAnsRepertory = releventQuestionAnsRepertory;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public Response create(Profile profile,String email) {
        try {
            Profile saveProfile = save(profile, email);
            if (saveProfile == null){
                ResponseBuilder.getInternalServerError();
            }
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,"Profile created",
                    modelMapper.map(saveProfile, ProfileResponseDto.class));
        } catch (UseNotfoundException e) {
            return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @Override
    public Response findByUserEmail(String email) {
        Optional<Profile> optionalProfile = optionalProfile(email);
        if (optionalProfile.isPresent()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "profile found",
                    modelMapper.map(optionalProfile.get(), ProfileResponseDto.class));
        } else {
            return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, "User profile not found");
        }
    }

    public Optional<Profile> optionalProfile(String email) {
        return profileRepository.findByUserEmail(email);
    }

    @Override
    public Profile save(Profile profile, String email) throws UseNotfoundException {
        Optional<User> byUsername = userService.findByEmail(email);
        if (byUsername.isPresent()) {
            log.info("user name is found " + byUsername.get().getUsername());
            profile.setUser(byUsername.get());
        } else {
            log.info("user name not found " + email);
            throw  new UseNotfoundException(email + " not found ");
        }
        profile.addSocialMediaLinks(profile.getSocialMediaLinks());
        if (profile.getRelevantQsAnsList() != null) {
            profile.setRelevantQsAnsList(profile.getRelevantQsAnsList());
//            releventQuestionAnsRepertory.saveAll(profile.getRelevantQsAnsList());
        }
        Profile saveProfile = profileRepository.save(profile);
        log.info("profile created successfully profile id: " + saveProfile.getId());
         return saveProfile;
    }

    @Override
    public Response update( Profile profile,String useEmail) {
        Optional<Profile> optionalProfile = profileRepository.findByUserEmail(useEmail);
        if (optionalProfile.isPresent()){
            profile.setId(optionalProfile.get().getId());
            try {
                Profile update = save(profile, useEmail);
                return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"profile updated",
                        modelMapper.map(update,ProfileResponseDto.class));
            } catch (UseNotfoundException e) {
                log.info(e.getMessage());
                return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,e.getMessage());
            }
        }
        log.info("profile not found "+useEmail);
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"profile not found id: "+useEmail);
    }
}
