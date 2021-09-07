package com.server.MemberManagement.service;

import com.server.MemberManagement.advice.exception.InvalidTokenException;
import com.server.MemberManagement.advice.exception.UserNotFoundException;
import com.server.MemberManagement.dto.*;
import com.server.MemberManagement.advice.exception.UserAlreadyExistsException;
import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.repository.MemberRepository;
import com.server.MemberManagement.security.JwtTokenProvider;
import com.server.MemberManagement.util.KeyUtil;
import com.server.MemberManagement.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final RedisUtil redisUtil;
    private final KeyUtil keyUtil;

    @Override
    public void signUp(MemberSignupRequestDto memberSignupDto) {
        if(!memberRepository.existsByUsername(memberSignupDto.getUsername())){
            memberSignupDto.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));

            memberRepository.save(memberSignupDto.toEntity());
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    //admin 확인하기 위해 만듦 실제 프로젝트에선 인증키를 받는 등 다른 방법 사용
    @Override
    public void signUpAdmin(MemberSignupRequestDto memberSignupDto) {
        if(memberRepository.findByUsername(memberSignupDto.getUsername()) != null){
            throw new UserAlreadyExistsException();
        }
        memberSignupDto.setPassword(passwordEncoder.encode(memberSignupDto.getPassword()));
        memberRepository.save(memberSignupDto.toEntityAdmin());
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

    @Override
    public void sendVerificationMail(EmailSendDto emailSendDto) {
        String authKey = keyUtil.getKey(6);
        redisUtil.setDataExpire(authKey, authKey, 60 * 30L);
        emailService.sendMail(emailSendDto.getEmail(), "비밀번호 변경 인증 이메일 입니다.", "인증번호 : "+authKey);
    }

    @Override
    public void verifyEmail(String key) {
        if (key.equals(redisUtil.getData(key))){
            redisUtil.deleteData(key);
        } else throw new InvalidTokenException();
    }

    @Override
    public void logout(String nickname) {
        redisUtil.deleteData(nickname);
    }

    @Override
    @Transactional
    public void changePassword(PasswordChangeDto passwordChangeDto) {
        Member findUser = memberRepository.findByUsername(passwordChangeDto.getUsername());
        if (findUser == null) throw new UserNotFoundException();
        if (passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), findUser.getPassword())) {
            findUser.updatePassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        }
    }

    @Override
    public void withdrawal(WithdrawalDto withdrawalDto) {
        Member findUser = memberRepository.findByUsername(withdrawalDto.getNickname());
        if (findUser == null) throw new UserNotFoundException();
        if (passwordEncoder.matches(withdrawalDto.getPassword(), findUser.getPassword())) {
            memberRepository.deleteById(findUser.getId());
        }
    }


}
