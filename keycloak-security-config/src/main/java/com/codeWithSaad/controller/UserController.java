package com.codeWithSaad.controller;

import com.codeWithSaad.model.User;
import com.codeWithSaad.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
            userService.createUser(user);
            return ResponseEntity.ok("User created successfully");
     }
}
