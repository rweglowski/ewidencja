package com.home.domain;

import com.home.service.MailService;
import com.home.service.PdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Created by radek on 08.04.2017.
 */
@Component
public class OtCommandHandler {

    @Autowired
    private MailService mailService;

    @Autowired
    private PdfService pdfService;

    public void handle(Ot ot){
        ot.setDate(LocalDate.now());
        pdfService.createOtPdf(ot);

        for(Employee employee : ot.getEmployees()){
            mailService.sendOtPdf(employee, ot.getName());
        }

    }
}
