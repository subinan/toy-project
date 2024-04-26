package com.example.toyproject.service;

import com.example.toyproject.domain.User;
import com.example.toyproject.dto.*;
import com.example.toyproject.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("유저 생성 Test")
    void 유저_생성() {
        // given
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setUserId("user");
        signUpRequestDTO.setPassword("1234");
        signUpRequestDTO.setNickname("닉네임");
        signUpRequestDTO.setName("이름");
        signUpRequestDTO.setPhoneNumber("010-1234-1234");
        signUpRequestDTO.setEmail("user@a.c");

        User user = new User("user", "1234", "닉네임", "이름", "010-1234-1234", "user@a.c");

        Long fakeId = 1L;
        ReflectionTestUtils.setField(user, "id", fakeId);

        // mocking
        given(userRepository.save(any()))
                .willReturn(user);
        given(userRepository.findById(fakeId))
                .willReturn(Optional.ofNullable(user));
        given(passwordEncoder.encode("1234"))
                .willReturn("encodedPassword");

        // when
        userService.createUser(signUpRequestDTO);

        // then
        User findUser = userRepository.findById(fakeId).get();
        assertEquals(user.getUserId(), findUser.getUserId());
        assertEquals(user.getPassword(), findUser.getPassword());
        assertEquals(user.getNickname(), findUser.getNickname());
        assertEquals(user.getName(), findUser.getName());
        assertEquals(user.getEmail(), findUser.getEmail());
        assertEquals(user.getPhoneNumber(), findUser.getPhoneNumber());
    }

    @Test
    @DisplayName("encode 메서드 호출 확인")
    void 패스워드_인코딩_확인() {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setUserId("user");
        signUpRequestDTO.setPassword("testPassword");
        signUpRequestDTO.setNickname("닉네임");
        signUpRequestDTO.setName("이름");
        signUpRequestDTO.setPhoneNumber("010-1234-1234");
        signUpRequestDTO.setEmail("user@a.c");

        User user = new User("user", "testPassword", "닉네임", "이름", "010-1234-1234", "user@a.c");

        when(userRepository.save(any())).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.createUser(signUpRequestDTO);

        verify(passwordEncoder, times(1)).encode("testPassword");
    }

    @Test
    @DisplayName("유저 생성 실패 Test: 중복된 아이디")
    void 유저_생성_실패() {
        // given
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setUserId("user");
        signUpRequestDTO.setPassword("1234");
        signUpRequestDTO.setNickname("닉네임");
        signUpRequestDTO.setName("이름");
        signUpRequestDTO.setPhoneNumber("010-1234-1234");
        signUpRequestDTO.setEmail("user@a.c");

        User user = new User("user", "1234", "닉네임", "이름", "010-1234-1234", "user@a.c");

        // mocking
        given(userRepository.findByUserId("user"))
                .willReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(signUpRequestDTO));
    }

    @Test
    @DisplayName("유저 조회 Test")
    void 유저_조회() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        List<User> userList = new ArrayList<>();
        userList.add(new User("user1", "password", "nickname", "name", "010-1234-1234", "user1@a.c"));
        userList.add(new User("user2", "password", "nickname", "name", "010-1234-1234", "user2@a.c"));

        Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());
        when(userRepository.findAll(pageable)).thenReturn(userPage);

        // when
        UserListResponseDTO userListResponseDTO = userService.getUsers(pageable);

        // then
        assertThat(userListResponseDTO.getUsers().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("getPageable 메서드 테스트: createdAt으로 정렬, 오름차순")
    void 페이징_가입순_오름차순() {
        // given
        int page = 1;
        int pageSize = 10;
        String sortKey = "createdAt";
        String sortOrder = "asc";

        // when
        Pageable pageable = userService.getPageable(page, pageSize, sortKey, sortOrder);

        // then
        assertEquals(PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "createdAt")), pageable);
    }

    @Test
    @DisplayName("getPageable 메서드 테스트: createdAt으로 정렬, 내림차순")
    void 페이징_가입순_내림차순() {
        // given
        int page = 1;
        int pageSize = 10;
        String sortKey = "createdAt";
        String sortOrder = "desc";

        // when
        Pageable pageable = userService.getPageable(page, pageSize, sortKey, sortOrder);

        // then
        assertEquals(PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")), pageable);
    }

    @Test
    @DisplayName("getPageable 메서드 테스트: userId로 정렬, 오름차순")
    void 페이징_아이디순_오름차순() {
        // given
        int page = 1;
        int pageSize = 10;
        String sortKey = "userId";
        String sortOrder = "asc";

        // when
        Pageable pageable = userService.getPageable(page, pageSize, sortKey, sortOrder);

        // then
        assertEquals(PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "userId")), pageable);
    }

    @Test
    @DisplayName("getPageable 메서드 테스트: userId로 정렬, 내림차순")
    void 페이징_아이디_내림차순() {
        // given
        int page = 1;
        int pageSize = 10;
        String sortKey = "userId";
        String sortOrder = "desc";

        // when
        Pageable pageable = userService.getPageable(page, pageSize, sortKey, sortOrder);

        // then
        assertEquals(PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "userId")), pageable);
    }

}