package com.example.joohyun.service;

import com.example.joohyun.entity.User;
import com.example.joohyun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User loggedinUser(String username) {
        if(username == null){
            return null;
        }
        return userRepository.getById(username);
    }

}
