package com.home.domain;

import com.home.domain.enumeration.EmployeeStatus;

import org.springframework.stereotype.Component;

@Component
public class EmployeeCommandHandler {

    public void handle(Employee employee){
        employee.setStatus(EmployeeStatus.ACTIVE);
    }
}
