package com.server.MemberManagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {

    private String username;
    private String password;
}
