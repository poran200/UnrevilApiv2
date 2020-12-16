package com.unriviel.api.service.impl;

import com.unriviel.api.annotation.CurrentUser;
import com.unriviel.api.dto.*;
import com.unriviel.api.exception.UserLogoutException;
import com.unriviel.api.model.*;
import com.unriviel.api.model.payload.LogOutRequest;
import com.unriviel.api.repository.RoleRepository;
import com.unriviel.api.repository.UserPaginationRepertory;
import com.unriviel.api.repository.UserRepository;
import com.unriviel.api.util.UrlConstrains;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.unriviel.api.util.ResponseBuilder.getSuccessResponseList;
import static com.unriviel.api.util.ResponseBuilder.getSuccessResponsePage;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository  roleRepository;
    private final UserDeviceService userDeviceService;
    private final RefreshTokenService refreshTokenService;
    private final UserPaginationRepertory userPaginationRepertory;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, UserDeviceService userDeviceService, RefreshTokenService refreshTokenService, UserPaginationRepertory userPaginationRepertory, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.userDeviceService = userDeviceService;
        this.refreshTokenService = refreshTokenService;
        this.userPaginationRepertory = userPaginationRepertory;
        this.modelMapper = modelMapper;
    }

    /**
     * Finds a user in the database by username
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user in the database by email
     */
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public UserResponse getUserInfo(CustomUserDetails customUserDetails){
        UserResponse dto = new UserResponse();
        dto.setUserName(customUserDetails.getUsername());
        dto.setEmail(customUserDetails.getEmail());
        dto.setFullName(customUserDetails.getFullName());
        dto.setRoles( customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        dto.setAccountNoneLocked(customUserDetails.isAccountNonLocked());
        dto.setEnable(customUserDetails.getActive());
        return  dto;
    }

    /**
     * Find a user in db by id.
     */
    public Optional<User> findById(Long Id) {
        return userRepository.findById(Id);
    }

    /**
     * Save the user to the database
     */
    @Transactional
    public User saveWithRole(User user,RoleName roleName) {
        Optional<Role> role = roleRepository.findByRole(roleName);
        Optional<Role> role1 = roleRepository.findByRole(RoleName.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        if (role.isPresent() ){
             roles.add(role.get());

            if (role1.isPresent()){
                roles.add(role1.get());
            }else {
                Role save = roleRepository.save(new Role(RoleName.ROLE_USER));
                roles.add(save);
            }
        }else {
            Role rolesave = roleRepository.save(new Role(roleName));
            roles.add(rolesave);
        }
         user.setRoles(roles);
        return userRepository.save(user);
    }
    public User save(User user){
       return userRepository.save(user);
    }

    /**
     * Check is the user exists given the email: naturalId
     */
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Check is the user exists given the username: naturalId
     */
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    /**
     * Creates a new user from the registration request
     */
    public User createUser(UserRegDto dto) {
        User newUser = new User();
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setUsername(dto.getUserName());
        newUser.setFullName(dto.getFullName());
//        newUser.addRole(new Role(roleName));
        newUser.setActive(true);
        newUser.setEmailVerified(false);
        return newUser;
    }

    /**
     * Performs a quick check to see what roles the new user could be assigned to.
     *
     * @return list of roles for the new user
     */
//    private Role getRolesForNewUser(RoleName roleName) {
//
//        Optional<Role> role = roleService.finByRoleName(roleName);
//         if (role.isPresent()){
//             logger.info("Setting user roles: " + role.get().getRole());
//             return role.get();
//         }else
//
//            return roleService.create(new Role(roleName));
//
//    }

    /**
     * Log the given user out and delete the refresh token associated with it. If no device
     * id is found matching the database for the given user, throw a log out exception.
     */
    public void logoutUser(@CurrentUser CustomUserDetails currentUser, LogOutRequest logOutRequest) {
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        UserDevice userDevice = userDeviceService.findByUserId(currentUser.getId())
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(logOutRequest.getDeviceInfo().getDeviceId(), "Invalid device Id supplied. No matching device found for the given user "));

        logger.info("Removing refresh token associated with device [" + userDevice + "]");
        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
    }
    public Response findAllReviewersByUserName(String userName){
        var userResponseDtoList = userPaginationRepertory.findAllByUsernameStartingWithAndRolesRole(userName, RoleName.ROLE_REVIEWER, PageRequest.of(0, 10))
                .map(user -> modelMapper.map(user, UserResponseDto.class)).getContent();
        return getSuccessResponseList(HttpStatus.OK,"reviewer list",userResponseDtoList,userResponseDtoList.size());
    }
    public Response finAllInfluencersByUserName(String userName){
        var userResponseDtoList = userPaginationRepertory.findAllByUsernameStartingWithAndRolesRole(userName, RoleName.ROLE_INFLUENCER, PageRequest.of(0, 10))
                .map(user -> modelMapper.map(user, UserResponseDto.class)).getContent();
        return getSuccessResponseList(HttpStatus.OK,"reviewer list",userResponseDtoList,userResponseDtoList.size());
    }
    public Response findAllReviewers(Pageable pageable){
        var userResponseDtoList = userPaginationRepertory.findAllByRolesRole(RoleName.ROLE_REVIEWER, pageable)
                .map(user -> modelMapper.map(user, ReviewerResponseDto.class)).getContent();
        return getSuccessResponseList(HttpStatus.OK,"reviewer list",userResponseDtoList,userResponseDtoList.size());
    }
    public Response findAllInfluencer(Pageable pageable,HttpServletRequest req){
        Page<InfluencerResponseDto> influencer = userPaginationRepertory.findAllByRolesRole(RoleName.ROLE_INFLUENCER, pageable)
                .map(user -> modelMapper.map(user, InfluencerResponseDto.class));
          influencer.getContent().forEach(dto-> dto.setProfileLink(ServletUriComponentsBuilder.fromRequest(req)
                  .replacePath(UrlConstrains.ProfileManagement.ROOT).path("/").path(dto.getEmail()).toUriString()));
        return getSuccessResponsePage(HttpStatus.OK,"reviewer list",influencer);
    }

    public boolean userAccountDisable(String email){
        var user = findByEmail(email);
        if (user.isPresent()){
            user.get().setActive(false);
            userRepository.save(user.get());
            return true;
        }
        return  false;
    }
    public boolean userAccountEnable(String email){
        var user = findByEmail(email);
        if (user.isPresent()){
            user.get().setActive(true);
            userRepository.save(user.get());
            return true;
        }
        return  false;
    }
}
