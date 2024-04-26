package com.example.toyproject.controller;

import com.example.toyproject.dto.*;
import com.example.toyproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원 가입", description = "회원을 등록합니다.")
    @ApiResponse(responseCode = "201", description = "success")
    @ApiResponse(responseCode = "400", description = "bad request")
    public ResponseEntity<UserInfoResponseDTO> join(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        UserInfoResponseDTO res = userService.createUser(signUpRequestDTO);
        return ResponseEntity.created(URI.create("/api/user/" + res.getUserId())).body(res);
    }

    @GetMapping("/list")
    @Operation(summary = "회원 조회", description = "회원을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "success")
    @ApiResponse(responseCode = "400", description = "bad request")
    public ResponseEntity<UserListResponseDTO> userList(
            @Parameter(description = "페이지 번호: 1부터 시작")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "한 페이지의 데이터 개수")
            @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "정렬 기준: createdAt 또는 name")
            @RequestParam(defaultValue = "userId") String sortKey,
            @Parameter(description = "정렬 방식: 오름차순(asc) 또는 내림차순(desc)")
            @RequestParam(defaultValue = "asc") String sortOrder) {

        if (page < 0 || pageSize < 1) throw new IllegalArgumentException("페이징 범위가 올바르지 않습니다.");

        Pageable pageable = userService.getPageable(page, pageSize, sortKey, sortOrder);
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "회원 수정", description = "회원을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "success")
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "invalid userId")
    public ResponseEntity<UserUpdateResponseDTO> userModify(@PathVariable String userId,
                                                            @RequestBody @Valid UserUpdateRequestDTO userUpdateRequestDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateRequestDTO));
    }

}
