package com.server.MemberManagement.security;

import com.server.MemberManagement.advice.exception.AccessTokenExpiredException;
import com.server.MemberManagement.advice.exception.InvalidTokenException;
import com.server.MemberManagement.advice.exception.UserNotFoundException;
import com.server.MemberManagement.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessJwt = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        String refreshJwt = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);
        String username = null;
        String refreshUsername = null;

//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 유효한 토큰인지 확인
        try {
            if (accessJwt != null && jwtTokenProvider.validateToken(accessJwt)){
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아온다.
                Authentication authentication = jwtTokenProvider.getAuthentication(accessJwt);
                username = jwtTokenProvider.getUserPk(accessJwt);

                if (username != null) {
                    System.out.println("username = " + username);
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e){
            if(refreshJwt != null){
                refreshUsername = jwtTokenProvider.getUserPk(refreshJwt);
                if(refreshUsername.equals(redisUtil.getData(refreshUsername))){
                    String newJwt = jwtTokenProvider.createToken(refreshUsername);
                    System.out.println("create new JWT!!!");
                    response.addHeader("JwtToken", newJwt);
                    System.out.println("newJwt = " + newJwt);
                }
            }else{
                throw new AccessTokenExpiredException();
            }
        } catch(MalformedJwtException e){
            throw new InvalidTokenException();
        } catch(IllegalArgumentException e){ //헤더에 토큰이 없으면 NPE 발생 하여 추가하였다. 추가적인 의미는 없다.
        }

        filterChain.doFilter(request, response);
    }
}

