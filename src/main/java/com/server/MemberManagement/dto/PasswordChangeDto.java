package com.server.MemberManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDto {

    private String username;
    private String currentPassword;
    private String newPassword;

}
