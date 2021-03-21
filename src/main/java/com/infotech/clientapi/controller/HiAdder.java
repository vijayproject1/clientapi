package com.infotech.clientapi.controller;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.util.List;

@Aspect
@Service
public class HiAdder {


    @AfterReturning(value = "(execution(com.infotech.clientapi.controller.encripy(..)) )", returning = "returnList")
    public void decrypt(StringBuffer returnList) throws Exception {
        System.out.println(returnList);
        returnList = returnList.append("Hi");
    }
}
