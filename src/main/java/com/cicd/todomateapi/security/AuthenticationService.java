package com.cicd.todomateapi.security;

import com.cicd.todomateapi.dto.MemberDTO;
import com.cicd.todomateapi.util.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AuthenticationService {

    protected AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JWTUtil jwtUtil;

    // 사용자 로그인 인증 및 토큰 생성
    public String authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        MemberDTO memberDTO = (MemberDTO) userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(memberDTO.getClaims(), 10);
    }
}
