package com.example.splitbill.service;

import com.example.splitbill.entity.UserEntity;
import com.example.splitbill.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserEntity register (UserEntity user){
        return userRepository.save(user);
    }
    public UserEntity login(String email, String password) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid Password");
        }

        return user;
    }
}
