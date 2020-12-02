package com.unriviel.api.util;

import com.unriviel.api.dto.ProfileDto;
import com.unriviel.api.dto.RelevenqsAnsDto;
import com.unriviel.api.model.Profile;
import com.unriviel.api.model.RelevantQsAns;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public  class  DtoToModel {
   final ModelMapper modelMapper;

    public DtoToModel(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public  Profile createProfile (ProfileDto dto){
        List<RelevenqsAnsDto> relevantQsAnsList = dto.getRelevantQsAnsList();
        Profile profile = modelMapper.map(dto, Profile.class);
        List<RelevantQsAns> list = new ArrayList<>();
        for (RelevenqsAnsDto relevenqsAnsDto: relevantQsAnsList){
            list .add(new RelevantQsAns(relevenqsAnsDto.getQuestion(),relevenqsAnsDto.getAnswer(),profile));
        }
        profile.setRelevantQsAnsList(list);
       return profile;
    }
}
