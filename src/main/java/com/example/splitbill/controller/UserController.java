package com.example.splitbill.controller;

import com.example.splitbill.dto.LoginRequest;
import com.example.splitbill.entity.UserEntity;
import com.example.splitbill.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user){
        return userService.register(user);
    }
    @PostMapping("/login")
    public UserEntity login(@RequestBody LoginRequest request) {

        return userService.login(
                request.getEmail(),
                request.getPassword()
        );
    }
}
