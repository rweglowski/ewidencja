package com.home.domain;

import com.home.service.MailService;
import com.home.service.PdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PtCommandHandler {

    @Autowired
    private MailService mailService;

    @Autowired
    private PdfService pdfService;

    public void handle(Pt pt){
        pdfService.createPtPdf(pt);

        for(Employee recipient : pt.getRecipients()){
            mailService.sendPtPdf(recipient, pt.getName());
        }
    }
}
