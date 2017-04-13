package com.home.domain;

import com.home.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by radek on 08.04.2017.
 */
@Component
public class OtCommandHandler {

    @Autowired
    private MailService mailService;

    public void handle(Ot ot){

        for(Employee employee : ot.getEmployees()){
            mailService.sendOtPdf(employee);
        }

    }
}
