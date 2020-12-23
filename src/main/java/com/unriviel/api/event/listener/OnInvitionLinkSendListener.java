package com.unriviel.api.event.listener;

import com.unriviel.api.event.OnInvitationLinkEvent;
import com.unriviel.api.exception.MailSendException;
import com.unriviel.api.service.impl.MailService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnInvitionLinkSendListener implements ApplicationListener<OnInvitationLinkEvent> {


     private final   MailService mailService;
      @Value("${app.client.server.host}")
     private String host;

     /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
      String reviewSubject =  "Invitation As  Reviewer";
     String influencerSubject =  "Invitation As  Influencer";
     @Autowired
    public OnInvitionLinkSendListener(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    @Async
    public void onApplicationEvent(final OnInvitationLinkEvent event) {
          this.invitationLink(event);
    }

    public void invitationLink(final OnInvitationLinkEvent event){

        String url = "http://"+host+"/invited/registration?Type="+event.getUserType()+"&email="+event.getEmail();
        try
        {
            if (event.getUserType().equals("reviewer")) {
                mailService.sendInvitationTest(url,reviewSubject,event.getEmail());
            }else {
                mailService.sendInvitationTest(url,influencerSubject,event.getEmail());
            }
        } catch (TemplateException | MessagingException | IOException e) {
            throw new MailSendException(event.getEmail(),"invitation email not send");
        }

    }



}
