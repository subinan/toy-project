package com.example.toyproject.controller;

import com.example.toyproject.dto.*;
import com.example.toyproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/list")
    public ResponseEntity<UserListResponseDTO> userList(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdAt").ascending().and(Sort.by("userId").ascending()));
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserUpdateResponseDTO> userModify(@PathVariable String userId,
                                                            @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateRequestDTO));
    }

}
