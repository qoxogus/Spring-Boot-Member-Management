package com.server.MemberManagement.service;

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

import javax.servlet.http.HttpServletRequest;
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

    @Override
    public void sendVerificationMail(EmailSendDto emailSendDto) {
        Member findUser = memberRepository.findByUsername(emailSendDto.getUsername());
        if (findUser == null) throw new UserNotFoundException();
        String authKey = keyUtil.getKey(6);
        redisUtil.setDataExpire(authKey, findUser.getUsername(), 60 * 30L);
        emailService.sendMail(findUser.getEmail(), "비밀번호 변경 인증 이메일 입니다.", "인증번호 : "+authKey);
    }

    @Override
    public void verifyEmail(String key) {
        String username = redisUtil.getData(key);
        Member findUser = memberRepository.findByUsername(username);
        if (findUser == null) throw new UserNotFoundException();
        redisUtil.deleteData(key);
    }

    @Override
    public void logout(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(accessToken);
        redisUtil.deleteData(username);
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
