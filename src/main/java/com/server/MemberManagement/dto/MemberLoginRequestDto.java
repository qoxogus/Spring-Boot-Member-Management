package com.server.MemberManagement.dto;

import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {

    private String username;
    private String password;

    public Member toEntity(){
        return Member.builder()
                .username(this.getUsername())
                .password(this.getPassword())
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();
    }
}
