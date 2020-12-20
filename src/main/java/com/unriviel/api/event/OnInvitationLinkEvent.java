package com.unriviel.api.event;

import org.springframework.context.ApplicationEvent;

public class OnInvitationLinkEvent extends ApplicationEvent {


    private final  String email;
    private final  String userType;
    public OnInvitationLinkEvent(String email, String userType) {
        super(email);
        this.email = email;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }


    public String getEmail() {
        return email;
    }
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */

}
