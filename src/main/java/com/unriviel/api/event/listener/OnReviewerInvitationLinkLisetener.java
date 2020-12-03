package com.unriviel.api.event.listener;

import com.unriviel.api.event.OnReviewerInvitationLinkEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class OnReviewerInvitationLinkLisetener implements ApplicationListener<OnReviewerInvitationLinkEvent> {
     @Value("${spring.mail.username}")
     private String sentFrom;
      @Autowired
      JavaMailSender mailSender;

     /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */

    @Override
    public void onApplicationEvent(final OnReviewerInvitationLinkEvent event) {
          this.invitationLink(event);
    }

    public void invitationLink(final OnReviewerInvitationLinkEvent event){
        final SimpleMailMessage email = constructEmailMessage(event, event.getEmail());
        mailSender.send(email);
    }
    private SimpleMailMessage constructEmailMessage(final OnReviewerInvitationLinkEvent event, final  String email) {
        final String subject = "Invitation As  Reviewer";
        final String confirmationUrl = event.getGetRridictLink().toUriString();
        final String message = "You are  successfully get  invitation Link . To confirm your registration, as Reviewer  please click on the below link.";
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + " \r\n" + confirmationUrl);
        mailMessage.setFrom(sentFrom);
        return mailMessage;
    }


}
