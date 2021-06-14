package com.server.MemberManagement.service;

import com.server.MemberManagement.advice.exception.UserNotFoundException;
import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.advice.exception.UserAlreadyExistsException;
import com.server.MemberManagement.model.Member;
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
    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) throw new UserNotFoundException();
        boolean passwordCheck = passwordEncoder.matches(password, member.getPassword());
        System.out.println("passwordCheck = " + passwordCheck);
        System.out.println("passwordCheck = " + passwordCheck);
        if (!passwordCheck) throw new UserNotFoundException();
        return member;
    }
}
