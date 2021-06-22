package com.server.MemberManagement.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RefreshTokenService {

    Map<String, String> getRefreshToken(HttpServletRequest request);
}
