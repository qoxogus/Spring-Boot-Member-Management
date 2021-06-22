package com.server.MemberManagement.controller;

import com.server.MemberManagement.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/refreshtoken")
    public Map<String, String> refresh(HttpServletRequest request) {
        return refreshTokenService.getRefreshToken(request);

    }
}
