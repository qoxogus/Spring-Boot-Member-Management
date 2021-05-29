package com.server.MemberManagement.dto;

import com.server.MemberManagement.model.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignupRequestDto {

    private String email;
    private String username;
    private String id;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .username(username)
                .id(id)
                .password(password)
                .build();
    }
}
