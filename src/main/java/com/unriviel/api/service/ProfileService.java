package com.unriviel.api.service;


import com.unriviel.api.dto.Response;
import com.unriviel.api.model.Profile;

public interface ProfileService {
    Response create(Profile profile);
    Response findByUseName(String userName);
    Profile save(Profile profile);
}
