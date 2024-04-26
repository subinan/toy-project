package com.example.toyproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하만 허용합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영어 대문자, 소문자, 숫자만 사용 가능합니다.")
    @Column(unique = true)
    private String userId;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자 이상만 허용합니다..")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$",
            message = "비밀번호는 최소한 하나의 알파벳(대소문자), 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank
    @Size(max = 20, message = "닉네임은 20자 이하만 허용합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 알파벳, 숫자, 한글만 사용 가능합니다.")
    private String nickname;

    @NotBlank
    @Size(max = 10, message = "이름은 10자 이하만 허용합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 알파벳과 한글만 사용 가능합니다.")
    private String name;

    @NotBlank
    @Pattern(regexp = "^01([016-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "핸드폰 번호 형식과 일치하지 않습니다.")
    private String phoneNumber;

    @NotBlank
    @Email(message = "이메일 형식과 일치하지 않습니다.")
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(String userId, String password, String nickname, String name, String phoneNumber, String email) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void updateInfo(String nickname, String name, String phoneNumber, String email) {
        this.nickname = nickname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}
