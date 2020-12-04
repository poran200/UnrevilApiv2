package com.unriviel.api.service.impl;


import com.unriviel.api.dto.ProfileResponseDto;
import com.unriviel.api.dto.Response;
import com.unriviel.api.exception.UserNotFoundException;
import com.unriviel.api.exception.UserProfileExistException;
import com.unriviel.api.model.Profile;
import com.unriviel.api.model.User;
import com.unriviel.api.repository.ProfileRepository;
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
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ProfileServiceImpl(ProfileRepository profileRepository, ModelMapper modelMapper, UserService userService) {
        this.profileRepository = profileRepository;
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
        } catch (UserProfileExistException e) {
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            log.info("user not found  "+ email);
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,e.getMessage());
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


    public Profile save(Profile profile, String email) throws UserProfileExistException, UserNotFoundException {
        Optional<User> byUsername = userService.findByEmail(email);

        if (byUsername.isEmpty()) {
            log.info("user name not found" + email);
            throw new UserNotFoundException("User not found!"+email);
        } else {
            Optional<Profile> optionalProfile = profileRepository.findByUserEmail(email);
            if (optionalProfile.isPresent()){
                log.info("user profile already exist "+email);
                throw  new UserProfileExistException(email + " User profile already  already exist ");
            }
                profile.setUser(byUsername.get());
            Profile saveProfile = profileRepository.save(profile);

            log.info("profile created successfully profile id: " + saveProfile.getId());
            return  saveProfile;
        }


    }

    @Override
    public Response update( Profile profile,String useEmail) {
        Optional<Profile> optionalProfile = profileRepository.findByIdAndUserEmail(profile.getId(),useEmail);
        if (optionalProfile.isPresent()){
            log.info("for update profile found by user email");
            Profile updateProfile = optionalProfile.get();
                    updateProfile.setId(profile.getId());
                    updateProfile.setUser(updateProfile.getUser());
                    updateProfile.setFaceBookPages(profile.getFaceBookPages());
                    updateProfile.setInstagramHandles(profile.getInstagramHandles());
                    updateProfile.setWebSiteOrBlogs(profile.getWebSiteOrBlogs());
                    updateProfile.setRelevantQsAns_1(profile.getRelevantQsAns_1());
                    updateProfile.setRelevantQsAns_2(profile.getRelevantQsAns_2());
                    updateProfile.setRelevantQsAns_3(profile.getRelevantQsAns_3());
              Profile updatedProfile = profileRepository.save(updateProfile);

            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "profile updated",modelMapper.map(updatedProfile,ProfileResponseDto.class));
        }

        log.info("profile not found by user email and profile id "+useEmail);
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"profile not found id: "+profile.getId()+
                " email:  "+ useEmail +". Make sure the user email and profile id ");
    }
}
