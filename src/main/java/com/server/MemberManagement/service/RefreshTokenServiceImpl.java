package com.server.MemberManagement.service;

import com.server.MemberManagement.advice.exception.TokenRefreshFailException;
import com.server.MemberManagement.advice.exception.UserNotFoundException;
import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.model.Role;
import com.server.MemberManagement.repository.MemberRepository;
import com.server.MemberManagement.security.JwtTokenProvider;
import com.server.MemberManagement.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    private long REDIS_EXPIRATION_TIME = JwtTokenProvider.REFRESH_TOKEN_VALIDATION_TIME; //6개월

    @Override
    public Map<String, String> getRefreshToken(String nickname, String refreshToken) {
        Map<String ,String> map = new HashMap<>();
        String newAccessToken = null;
        String newRefreshToken = null;

        Member findUser = memberRepository.findByUsername(nickname);
        List<Role> roles = findUser.getRoles();

        if (redisUtil.getData(nickname).equals(refreshToken) && jwtTokenProvider.validateToken(refreshToken)) {
            redisUtil.deleteData(nickname);//refreshToken이 저장되어있는 레디스 초기화 후

            newAccessToken = jwtTokenProvider.createToken(nickname, roles);
            newRefreshToken = jwtTokenProvider.createRefreshToken();

            redisUtil.setDataExpire(nickname, newRefreshToken, REDIS_EXPIRATION_TIME); //새 refreshToken을 다시 저장

            map.put("nickname", nickname);
            map.put("NewAccessToken", "Bearer " + newAccessToken); // NewAccessToken 반환
            map.put("NewRefreshToken", "Bearer " + newRefreshToken); // NewRefreshToken 반환

            return map;
        } else {
            throw new TokenRefreshFailException(); // token 재발급 실패 Exception
        }
    }
}