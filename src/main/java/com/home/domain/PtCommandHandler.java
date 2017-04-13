package com.home.domain;

import com.home.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PtCommandHandler {

    @Autowired
    private MailService mailService;

    public void handle(Pt pt){
        for(Employee recipient : pt.getRecipients()){
            mailService.sendPtPdf(recipient);
        }
    }
}
