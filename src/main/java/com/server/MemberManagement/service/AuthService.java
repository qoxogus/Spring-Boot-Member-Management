package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthService {
    void signUp(MemberSignupRequestDto memberSignupDto);

    void signUpAdmin(MemberSignupRequestDto memberSignupDto);

    Map<String, String> login(MemberLoginRequestDto loginDto);

    void sendVerificationMail(EmailSendDto emailSendDto);

    void verifyEmail(String key);

    void logout(HttpServletRequest request);

    void changePassword(PasswordChangeDto passwordChangeDto);

    void withdrawal(WithdrawalDto withdrawalDto);
}
