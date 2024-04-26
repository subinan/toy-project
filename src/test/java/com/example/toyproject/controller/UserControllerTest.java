package com.example.toyproject.controller;

import com.example.toyproject.domain.User;
import com.example.toyproject.dto.SignUpRequestDTO;
import com.example.toyproject.dto.UserUpdateRequestDTO;
import com.example.toyproject.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("회원 가입 Test")
    void 회원_가입() throws Exception {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setUserId("user");
        signUpRequestDTO.setPassword("Password1!");
        signUpRequestDTO.setNickname("닉네임");
        signUpRequestDTO.setName("이름");
        signUpRequestDTO.setPhoneNumber("010-1234-1234");
        signUpRequestDTO.setEmail("user@a.c");

        this.mockMvc
                .perform(post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 목록 Test")
    void 회원_목록() throws Exception {
        User user1 = new User("user1", "Password1!", "nickname", "name", "010-1234-1234", "user@a.c");
        User user2 = new User("user2", "Password1!", "nickname", "name", "010-1234-1234", "user@a.c");
        userRepository.save(user1);
        userRepository.save(user2);

        this.mockMvc
                .perform(get("/api/user/list?sortKey=createdAt&sortOrder=asc"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 목록 실패 Test: 페이징 에러")
    void 회원_목록_실패() throws Exception {
        this.mockMvc
                .perform(get("/api/user/list?page-1&pageSize=0"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 수정 Test")
    void 회원_수정() throws Exception {
        User user = new User("user", "Password1!", "nickname", "name", "010-1234-1234", "user@a.c");
        userRepository.save(user);

        String userId = "user";
        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO();
        userUpdateRequestDTO.setNickname("newNickname");
        userUpdateRequestDTO.setName("newName");
        userUpdateRequestDTO.setPhoneNumber("010-4321-4321");
        userUpdateRequestDTO.setEmail("newUser@a.c");

        this.mockMvc
                .perform(put("/api/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 수정 실패 Test: 존재하지 않는 회원")
    void 회원_수정_실패() throws Exception {
        String userId = "user";
        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO();
        userUpdateRequestDTO.setNickname("newNickname");
        userUpdateRequestDTO.setName("newName");
        userUpdateRequestDTO.setPhoneNumber("010-4321-4321");
        userUpdateRequestDTO.setEmail("newUser@a.c");

        this.mockMvc
                .perform(put("/api/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDTO)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}