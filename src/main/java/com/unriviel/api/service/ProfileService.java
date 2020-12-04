package com.unriviel.api.service;


import com.unriviel.api.dto.Response;
import com.unriviel.api.exception.UserNotFoundException;
import com.unriviel.api.exception.UserProfileExistException;
import com.unriviel.api.model.Profile;

public interface ProfileService {
    Response create(Profile profile,String userEmail);
    Response findByUserEmail(String userName);
    Profile save(Profile profile, String userEmail) throws  UserNotFoundException, UserProfileExistException;

    Response update(Profile profile, String useEmail);
}
