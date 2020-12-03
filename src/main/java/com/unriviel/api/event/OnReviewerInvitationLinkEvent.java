package com.unriviel.api.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class OnReviewerInvitationLinkEvent extends ApplicationEvent {

    private transient UriComponentsBuilder getRridictLink;
    private final  String email;

    public OnReviewerInvitationLinkEvent(UriComponentsBuilder source, String email) {
        super(source);
        this.getRridictLink = source;
        this.email = email;
    }

    public UriComponentsBuilder getGetRridictLink() {
        return getRridictLink;
    }

    public void setGetRridictLink(UriComponentsBuilder getRridictLink) {
        this.getRridictLink = getRridictLink;
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
