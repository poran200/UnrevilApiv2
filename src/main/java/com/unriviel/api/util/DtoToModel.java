package com.unriviel.api.util;

import com.unriviel.api.dto.ProfileDto;
import com.unriviel.api.model.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public  class  DtoToModel {
   final ModelMapper modelMapper;

    public DtoToModel(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public  Profile createProfile (ProfileDto dto){
        return modelMapper.map(dto, Profile.class);
    }
}
