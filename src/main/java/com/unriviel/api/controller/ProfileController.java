package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.Response;
import com.unriviel.api.service.ProfileService;
import com.unriviel.api.util.ResponseBuilder;
import com.unriviel.api.util.UrlConstrains;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@APiController
@RequestMapping(UrlConstrains.ProfileManagement.ROOT)
public class ProfileController {
    private  final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @GetMapping(UrlConstrains.ProfileManagement.FIND_BY_USER_NAME)
    public ResponseEntity<Object> findByUsername(@PathVariable(required = true) String userName){
        Response response;
        if(userName != null){
            response = profileService.findByUseName(userName.trim());
        }else {
            response = ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"username is require");
        }
         return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }

}
