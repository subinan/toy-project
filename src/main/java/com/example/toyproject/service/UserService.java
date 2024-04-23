package com.example.toyproject.service;

import com.example.toyproject.domain.User;
import com.example.toyproject.dto.*;
import com.example.toyproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(SignUpRequestDTO signUpRequestDTO) {
        User user = SignUpRequestDTO.toUser(signUpRequestDTO);
        userRepository.save(user);
    }

}
