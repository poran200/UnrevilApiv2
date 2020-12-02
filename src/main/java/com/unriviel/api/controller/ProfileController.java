package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.ProfileDto;
import com.unriviel.api.dto.ProfileResponseDto;
import com.unriviel.api.dto.Response;
import com.unriviel.api.model.Profile;
import com.unriviel.api.service.ProfileService;
import com.unriviel.api.util.DtoToModel;
import com.unriviel.api.util.ResponseBuilder;
import com.unriviel.api.util.UrlConstrains;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@APiController
@RequestMapping(UrlConstrains.ProfileManagement.ROOT)
public class ProfileController   {
    private  final ProfileService profileService;
   private final DtoToModel dtoToModel;
   private final ModelMapper modelMapper;
    public ProfileController(ProfileService profileService, DtoToModel dtoToModel, ModelMapper modelMapper) {
        this.profileService = profileService;
        this.dtoToModel = dtoToModel;
        this.modelMapper = modelMapper;
    }
    @GetMapping(UrlConstrains.ProfileManagement.FIND_BY_USER_NAME)
    public ResponseEntity<Object> findByUsername(@PathVariable(required = true) String userEmail){
        Response response;
        if(userEmail != null){
            response = profileService.findByUserEmail(userEmail.trim());
        }else {
            response = ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"username is require");
        }
         return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
    }



//    @PreAuthorize("hasRole('INFLUENCER')")
    @PostMapping(UrlConstrains.ProfileManagement.CREATE_PROFILE)
    public ResponseEntity<Object> create(@Valid  @PathVariable(required = true) String userEmail,
                                        @Valid @RequestBody(required = true) ProfileDto dto){
        Response response;
        if(userEmail != null){
            Profile profile = dtoToModel.createProfile(dto);
            response = profileService.create(profile,userEmail.trim());
        }else {
            response = ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"useEmail is require");
        }
        String s = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/" + userEmail)
                .toUriString();
        System.out.println(s);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
   @PutMapping(UrlConstrains.ProfileManagement.UPDATE)
    public ResponseEntity<Object> update(@PathVariable(required = true) String userEmail
           ,@Valid @RequestBody ProfileResponseDto dto){
       Profile profile = modelMapper.map(dto, Profile.class);
       Response response = profileService.update(profile, userEmail);
       return ResponseEntity.status((int) response.getStatusCode()).body(response);

   }

}
