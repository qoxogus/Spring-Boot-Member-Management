package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.EmailSendDto;
import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.dto.PasswordChangeDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthService {
    void signUp(MemberSignupRequestDto memberSignupDto);

    Map<String, String> login(MemberLoginRequestDto loginDto);

    void sendVerificationMail(EmailSendDto emailSendDto);

    void verifyEmail(String key);

    void logout(HttpServletRequest request);

    void changePassword(PasswordChangeDto passwordChangeDto);
}
