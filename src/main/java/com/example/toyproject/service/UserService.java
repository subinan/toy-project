package com.example.toyproject.service;

import com.example.toyproject.domain.User;
import com.example.toyproject.dto.*;
import com.example.toyproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(SignUpRequestDTO signUpRequestDTO) {
        checkUserId(signUpRequestDTO.getUserId());

        User user = SignUpRequestDTO.toUser(signUpRequestDTO);

        String encodedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());
        user.setEncodedPassword(encodedPassword);

        userRepository.save(user);
    }

    private void checkUserId(String userId) {
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
    }

    public UserListResponseDTO getUsers(Pageable pageable) {
        Page<UserInfoResponseDTO> userPage = userRepository.findAll(pageable).map(UserInfoResponseDTO::fromUser);
        PaginationInfoDto paginationInfoDto = new PaginationInfoDto(pageable.getPageNumber() + 1, userPage.getSize(), userPage.getTotalPages());
        return new UserListResponseDTO(userPage.getContent(), paginationInfoDto);
    }

    public Pageable getPageable(int page, int pageSize, String sortKey, String sortOrder) {
        Sort sort = getSort(sortKey, sortOrder);
        return PageRequest.of(page - 1, pageSize, sort);
    }

    private Sort getSort(String sortKey, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        switch (sortKey) {
            case "createdAt":
                return Sort.by(direction, "createdAt");
            default:
                return Sort.by(direction, "userId");
        }
    }

    public UserUpdateResponseDTO updateUser(String userId, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));
        user.updateInfo(userUpdateRequestDTO.getNickname(),
                userUpdateRequestDTO.getName(),
                userUpdateRequestDTO.getPhoneNumber(),
                userUpdateRequestDTO.getEmail());
        User updatedUser = userRepository.save(user);
        return UserUpdateResponseDTO.fromUser(updatedUser);
    }

}
