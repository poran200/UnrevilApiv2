package com.unriviel.api.service.impl;


import com.unriviel.api.dto.ProfileDto;
import com.unriviel.api.dto.Response;
import com.unriviel.api.model.Profile;
import com.unriviel.api.model.RelevantQsAns;
import com.unriviel.api.repository.ProfileRepository;
import com.unriviel.api.repository.ReleventQuestionAnsRepertory;
import com.unriviel.api.service.ProfileService;
import com.unriviel.api.util.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ReleventQuestionAnsRepertory relevantQuestionsRepository;
    private final ModelMapper modelMapper;
    public ProfileServiceImpl(ProfileRepository profileRepository, ReleventQuestionAnsRepertory relevantQuestionsRepository, ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.relevantQuestionsRepository = relevantQuestionsRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public Response create(Profile profile) {
        return null;
    }

    @Override
    public Response findByUseName(String userName) {
        Optional<Profile> optionalProfile = profileRepository.findByUserUsername(userName);
        if (optionalProfile.isPresent()){
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"profile found",
                    modelMapper.map(optionalProfile.get(), ProfileDto.class));
        }else {
            return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"User profile not found");
        }
    }

    @Override
    public Profile save(Profile profile) {
        List<RelevantQsAns> relevantQsAnsList = profile.getRelevantQsAnsList();
        Profile saveProfile = profileRepository.save(profile);
        for (RelevantQsAns qs :
                relevantQsAnsList) {
            qs.setProfile(saveProfile);
        }
        relevantQuestionsRepository.saveAll(relevantQsAnsList);
        return saveProfile;
    }
}
