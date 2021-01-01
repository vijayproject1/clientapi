package com.infotech.clientapi.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/client")
public class ClientApiController {


    private static final Logger logger = LoggerFactory.getLogger(ClientApiController.class);
    @Value("${client.value}")
    String clientValue;


    @GetMapping("/sample")
    public String getUserName() {
        logger.info("sample Request Received");
        return "Hi "+ clientValue;
    }
}
