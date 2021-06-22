package com.server.MemberManagement.service;

import com.server.MemberManagement.advice.exception.UserNotFoundException;
import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.advice.exception.UserAlreadyExistsException;
import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.repository.MemberRepository;
import com.server.MemberManagement.security.JwtTokenProvider;
import com.server.MemberManagement.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Override
    public void signUp(MemberSignupRequestDto memberSignupDto) {
        if(memberRepository.findByUsername(memberSignupDto.getUsername()) != null){
            throw new UserAlreadyExistsException();
        }
        memberSignupDto.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));
        memberRepository.save(memberSignupDto.toEntity());
    }

    @Override
    public Map<String, String> login(MemberLoginRequestDto loginDto) {
        Member findUser = memberRepository.findByUsername(loginDto.getUsername());
        if (findUser == null) throw new UserNotFoundException();
        // 비밀번호 검증
        boolean passwordCheck = passwordEncoder.matches(loginDto.getPassword(), findUser.getPassword());
        if (!passwordCheck) throw new UserNotFoundException();

        String accessToken = jwtTokenProvider.createToken(loginDto.getUsername(), loginDto.toEntity().getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisUtil.deleteData(loginDto.getUsername()); // accessToken이 만료되지않아도 로그인 할 때 refreshToken도 초기화해서 다시 생성 후 redis에 저장한다.
        redisUtil.setDataExpire(loginDto.getUsername(), refreshToken, 360000 * 1000l* 24 * 180);
        Map<String ,String> map = new HashMap<>();
        map.put("nickname", loginDto.getUsername());
        map.put("accessToken", "Bearer " + accessToken); // accessToken 반환
        map.put("refreshToken", "Bearer " + refreshToken); // refreshToken 반환

        return map;
    }
}
