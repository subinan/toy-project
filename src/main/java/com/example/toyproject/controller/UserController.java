package com.example.toyproject.controller;

import com.example.toyproject.dto.*;
import com.example.toyproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        userService.createUser(signUpRequestDTO);
        return ResponseEntity.created(URI.create("/api/user/" + signUpRequestDTO.getUserId())).build();
    }

}
