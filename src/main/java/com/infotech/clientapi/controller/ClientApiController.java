package com.infotech.clientapi.controller;


import com.infotech.clientapi.dao.UserRepository;
import com.infotech.clientapi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/user")
public class ClientApiController {

    @Autowired
    UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(ClientApiController.class);
    @Value("${client.value}")
    String clientValue;


    @GetMapping("/sample")
    public String getUserName() {
        logger.info("sample Request Received");
        return "Hi "+ clientValue;
    }


    @GetMapping("/{user}/{pass}")
    public User addUser(@PathVariable("user") String user,
                        @PathVariable("pass") String pass ) {
        logger.info("User Request Received {} ,{} ", user, pass);
        return userRepository.save(new User(user,pass));
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        logger.info("GET User Request Received ");
        return userRepository.findAll();
    }
}
