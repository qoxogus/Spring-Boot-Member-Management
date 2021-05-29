package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.advice.exception.UserAlreadyExistsException;
import com.server.MemberManagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(MemberSignupRequestDto memberSignupDto) {
        if(memberRepository.findByUsername(memberSignupDto.getUsername()) != null){
            throw new UserAlreadyExistsException();
        }
        memberSignupDto.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));
        memberRepository.save(memberSignupDto.toEntity());
    }

    @Override
    public MemberLoginRequestDto login(String id, String password) {
        return null;
    }
}
