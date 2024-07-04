package com.cicd.todomateapi.security.handler;

import com.cicd.todomateapi.dto.MemberDTO;
import com.cicd.todomateapi.security.AuthenticationService;
import com.cicd.todomateapi.util.JWTUtil;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

// 로그인 성공시 실행할 클래스 (config 등록하면 onAuthenticationSuccess 자동 실행됨)
@Slf4j
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 로그인 인증 성공 후 사용자 객체를 가져온다.
        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        // AuthenticationService를 사용하여 JWT 토큰 생성
        String jwtToken = null;
        try {
            jwtToken = authenticationService.authenticate(memberDTO.getUsername(), memberDTO.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 생성된 JWT 토큰을 응답 헤더에 추가한다.
        response.addHeader("Authorization", "Bearer " + jwtToken);

        // 원래 성공 처리 로직 수행 (기본 URL 이동 등)
//        super.onAuthenticationSuccess(request, response, authentication);
    }
}
