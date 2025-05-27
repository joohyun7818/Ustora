package com.example.joohyun.service;

import com.example.joohyun.dto.UserDTO;
import com.example.joohyun.entity.User;
import com.example.joohyun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User loggedinUser(String username) {
        if(username == null){
            return null;
        }
        return userRepository.getById(username);
    }

    public User findByEmail(String email) {
        return userRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDTO convertToDTO(User user) {
        return UserDTO.fromEntity(user);
    }
}
