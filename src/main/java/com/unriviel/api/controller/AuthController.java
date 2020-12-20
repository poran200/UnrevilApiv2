package com.unriviel.api.controller;

import com.unriviel.api.dto.*;
import com.unriviel.api.event.*;
import com.unriviel.api.exception.*;
import com.unriviel.api.model.CustomUserDetails;
import com.unriviel.api.model.DeviceType;
import com.unriviel.api.model.RoleName;
import com.unriviel.api.model.payload.*;
import com.unriviel.api.model.token.EmailVerificationToken;
import com.unriviel.api.security.JwtTokenProvider;
import com.unriviel.api.service.impl.AuthService;
import com.unriviel.api.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

import static com.unriviel.api.util.UrlConstrains.ProfileManagement.ROOT;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authorization Rest API", description = "Defines endpoints that can be hit only when the user is not logged in. It's not secured by default.")
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class);
    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserService userService;
    @Value("${app.client.server.host}")
    private  String clientServerHost;

    @Autowired
    public AuthController(AuthService authService, JwtTokenProvider tokenProvider, ApplicationEventPublisher applicationEventPublisher, UserService userService) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userService = userService;
    }

    /**
     * Checks is a given email is in use or not.
     */
    @Operation(description = "Checks if the given email is in use")
    @GetMapping("/checkEmailInUse")
    public ResponseEntity checkEmailInUse(@Parameter(description = "Email id to check against") @RequestParam("email") String email) {
        Boolean emailExists = authService.emailAlreadyExists(email);
        return ResponseEntity.ok(new ApiResponse(true, emailExists.toString()));
    }

    /**
     * Checks is a given username is in use or not.
     */
    @Operation(description = "Checks if the given username is in use")
    @GetMapping("/checkUsernameInUse")
    public ResponseEntity checkUsernameInUse(@Parameter(description = "Username to check against") @RequestParam(
            "username") String username) {
        Boolean usernameExists = authService.usernameAlreadyExists(username);
        return ResponseEntity.ok(new ApiResponse(true, usernameExists.toString()));
    }


    /**
     * Entry point for the user log in. Return the jwt auth token and the refresh token
     */
    @PostMapping("/login")
    @Operation(description = "Logs the user in to the system and return the auth tokens")
    public ResponseEntity authenticateUser(@Parameter(description = "The LoginRequest payload") @Valid @RequestBody LoginDto loginDto) {
        LoginRequest loginRequest = getLoginRequest(loginDto);
        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.info("Logged in User returned [API]: " + customUserDetails.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserResponse userResponse = getUserResponse(customUserDetails);
                    String jwtToken = authService.generateToken(customUserDetails);
                    JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwtToken, null, tokenProvider.getExpiryDuration());
                    LoginSussesResponse loginSussesResponse = new LoginSussesResponse(userResponse, jwtAuthenticationResponse);
                    return ResponseEntity.ok().body(loginSussesResponse);

    }

    private UserResponse getUserResponse(CustomUserDetails customUserDetails) {
        UserResponse userResponse = userService.getUserInfo(customUserDetails);
        UriComponentsBuilder profileLink = ServletUriComponentsBuilder.fromCurrentContextPath().path(ROOT + "/" + userResponse.getEmail());
        userResponse.setProfileLink(profileLink.toUriString());
        return userResponse;
    }

    private LoginRequest getLoginRequest(LoginDto loginDto) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(loginDto.getUserEmail());
        loginRequest.setPassword(loginDto.getPassword());
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceId("258");
        deviceInfo.setDeviceType(DeviceType.DEVICE_TYPE_WEB);
        loginRequest.setDeviceInfo(deviceInfo);
        return loginRequest;
    }

    /**
     * Entry point for the user registration process. On successful registration,
     * publish an event to generate email verification token
     */
//    @PostMapping("admin/register")
////    @PreAuthorize("hasRole('ADMIN')")
//    @Operation(description = "Registers the user and publishes an event to generate the email verification")
////    @DataValidation
//    public ResponseEntity registerAdmin(@Parameter(description = "The RegistrationRequest payload") @Valid @RequestBody UserRegDto userRegDto) {
//         return    registrationUser(userRegDto,RoleName.ROLE_ADMIN);
//    }
    @PostMapping("reviewer/register")
    @Operation(description = "Registers the reviewer and publishes an event to generate the email verification")
    public ResponseEntity registerReeviewer(@Parameter(description = "The RegistrationRequest payload") @Valid @RequestBody UserRegDto userRegDto) {
        return    registrationUser(userRegDto,RoleName.ROLE_REVIEWER);
    }
    @PostMapping("influencer/register")
    @Operation(description = "Registers the user and publishes an event to generate the email verification")
    public ResponseEntity registerUser(@Parameter(description = "The RegistrationRequest payload") @Valid @RequestBody UserRegDto userRegDto) {
        return    registrationUser(userRegDto,RoleName.ROLE_INFLUENCER);
    }

    /**
     * Receives the reset link request and publishes an event to send email id containing
     * the reset link if the request is valid. In future the deeplink should open within
     * the app itself.
     */
    @PostMapping("/password/resetlink")
    @Operation(description = "Receive the reset link request and publish event to send mail containing the password " +
            "reset link")
    public ResponseEntity resetLink(@Parameter(description = "The PasswordResetLinkRequest payload") @Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest) {

        return authService.generatePasswordResetToken(passwordResetLinkRequest)
                .map(passwordResetToken -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/password/reset");
                    OnGenerateResetLinkEvent generateResetLinkMailEvent = new OnGenerateResetLinkEvent(passwordResetToken,
                            urlBuilder);
                    applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
                    return ResponseEntity.ok(new ApiResponse(true, "Password reset link sent successfully"));
                })
                .orElseThrow(() -> new PasswordResetLinkException(passwordResetLinkRequest.getEmail(), "Couldn't create a valid token"));
    }

    /**
     * Receives a new passwordResetRequest and sends the acknowledgement after
     * changing the password to the user's mail through the event.
     */

    @PostMapping("/password/reset")
    @Operation(description = "Reset the password after verification and publish an event to send the acknowledgement " +
            "email")
    public ResponseEntity resetPassword(@Parameter(description = "The PasswordResetRequest payload") @Valid @RequestBody PasswordResetRequest passwordResetRequest) {

        return authService.resetPassword(passwordResetRequest)
                .map(changedUser -> {
                    OnUserAccountChangeEvent onPasswordChangeEvent = new OnUserAccountChangeEvent(changedUser, "Reset Password",
                            "Changed Successfully");
                    applicationEventPublisher.publishEvent(onPasswordChangeEvent);
                    return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
                })
                .orElseThrow(() -> new PasswordResetException(passwordResetRequest.getToken(), "Error in resetting password"));
    }

    /**
     * Confirm the email verification token generated for the user during
     * registration. If token is invalid or token is expired, report error.
     */
    @GetMapping("/registrationConfirmation")
    @Operation(description = "Confirms the email verification token that has been generated for the user during registration")
    public ResponseEntity confirmRegistration(@Parameter(description = "the token that was sent to the user email") @RequestParam("token") String token) {

        return authService.confirmEmailRegistration(token)
                .map(user -> ResponseEntity.ok(new ApiResponse(true, "User verified successfully")))
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", token, "Failed to confirm. Please generate a new email verification request"));
    }


    /**
     * Resend the email registration mail with an updated token expiry. Safe to
     * assume that the user would always click on the last re-verification email and
     * any attempts at generating new token from past (possibly archived/deleted)
     * tokens should fail and report an exception.
     */
    @GetMapping("/resendRegistrationToken")
    @Operation(description = "Resend the email registration with an updated token expiry. Safe to " +
            "assume that the user would always click on the last re-verification email and " +
            "any attempts at generating new token from past (possibly archived/deleted)" +
            "tokens should fail and report an exception. ")
    public ResponseEntity resendRegistrationToken(@Parameter(description = "the initial token that was sent to the user email after registration") @RequestParam("token") String existingToken) {

        EmailVerificationToken newEmailToken = authService.recreateRegistrationToken(existingToken)
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken, "User is already registered. No need to re-generate token"));

        return Optional.ofNullable(newEmailToken.getUser())
                .map(registeredUser -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromUriString("http://"+clientServerHost+"/confirmation?");
                    OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent = new OnRegenerateEmailVerificationEvent(registeredUser, urlBuilder, newEmailToken);
                    applicationEventPublisher.publishEvent(regenerateEmailVerificationEvent);
                    return ResponseEntity.ok(new ApiResponse(true, "Email verification resent successfully"));
                })
                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken, "No user associated with this request. Re-verification denied"));
    }

    /**
     * Refresh the expired jwt token using a refresh token for the specific device
     * and return a new token to the caller
     */
    @PostMapping("/refresh")
    @Operation(description = "Refresh the expired jwt authentication by issuing a token refresh request and returns the" +
            "updated response tokens")
    public ResponseEntity refreshJwtToken(@Parameter(description = "The TokenRefreshRequest payload") @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        return authService.refreshJwtToken(tokenRefreshRequest)
                .map(updatedToken -> {
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    logger.info("Created new Jwt Auth token: " + updatedToken);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(updatedToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(), "Unexpected error during token refresh. Please logout and login again."));
    }


    public ResponseEntity registrationUser(UserRegDto dto ,RoleName roleName){
        return authService.registerUser(dto, roleName )
                .map(user -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromUriString("http://"+clientServerHost+":/confirmation?");
                    OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent = new OnUserRegistrationCompleteEvent(user, urlBuilder);
                    applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
                    logger.info("Registered User returned [API[: " + user);
                    return ResponseEntity.ok(new ApiResponse(true, "User registered successfully. Check your email for verification"));
                })
                .orElseThrow(() -> new UserRegistrationException(dto.getEmail(), "Missing user object in database"));
    }

    @PostMapping("/sendReviewerInviteLink")
    public ResponseEntity setInvitationLinkToReviewer(@Valid @RequestBody(required = true) InviteEmailDto dto){

        OnInvitationLinkEvent reviewerInvitationLinkEvent = new OnInvitationLinkEvent(dto.getEmail(), "reviewer");
        applicationEventPublisher.publishEvent(reviewerInvitationLinkEvent);
        return ResponseEntity.ok().body(new ApiResponse(true,"Invitation link send successfully "));
    }
    @PostMapping("/sendInfluencerInviteLink")
    public ResponseEntity setInvitationLinkToInfluencer(@Valid @RequestBody(required = true) InviteEmailDto dto){

        OnInvitationLinkEvent reviewerInvitationLinkEvent = new OnInvitationLinkEvent(dto.getEmail(), "influencer");
        applicationEventPublisher.publishEvent(reviewerInvitationLinkEvent);
        return ResponseEntity.ok().body(new ApiResponse(true,"Invitation link send successfully "));
    }
}
