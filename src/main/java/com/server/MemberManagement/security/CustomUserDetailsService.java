package com.server.MemberManagement.security;

import com.server.MemberManagement.advice.exception.UserNotFoundException;
import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        Member findMember = memberRepository.findByUsername(username);
        return (UserDetails) findMember;
    }
}
