package com.infotech.clientapi.dao;


import com.infotech.clientapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
