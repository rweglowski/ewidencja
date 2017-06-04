package com.home.domain;

import com.home.service.MailService;
import com.home.service.PdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PtCommandHandler {

    @Autowired
    private MailService mailService;

    @Autowired
    private PdfService pdfService;

    public void handle(Pt pt){
        pt.setDate(LocalDate.now());
        pdfService.createPtPdf(pt);

        for(Employee recipient : pt.getRecipients()){
            mailService.sendPtPdf(pt, recipient, pt.getName());
        }
    }
}
