package com.example.toyproject.service;

import com.example.toyproject.domain.User;
import com.example.toyproject.dto.*;
import com.example.toyproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(SignUpRequestDTO signUpRequestDTO) {
        User user = SignUpRequestDTO.toUser(signUpRequestDTO);
        userRepository.save(user);
    }

    public UserListResponseDTO getUsers(Pageable pageable) {
        Page<UserInfoResponseDTO> userPage = userRepository.findAll(pageable).map(UserInfoResponseDTO::fromUser);
        PaginationInfoDto paginationInfoDto = new PaginationInfoDto(pageable.getPageNumber() + 1, userPage.getSize(), userPage.getTotalPages());
        return new UserListResponseDTO(userPage.getContent(), paginationInfoDto);
    }

}
