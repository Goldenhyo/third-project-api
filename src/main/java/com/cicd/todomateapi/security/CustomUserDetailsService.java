package com.cicd.todomateapi.security;

import com.cicd.todomateapi.domain.Member;
import com.cicd.todomateapi.dto.MemberDTO;
import com.cicd.todomateapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 이메일로 회원 조회하고 MemberDTO 리턴, 일치하는 이메일 없으면 예외 발생 시키기
        log.info("********** CustomUserDetailsService - loadUserByUsername - username:{}", username);
        Member member = memberRepository.findMemberByEmail(username).orElseThrow();
        if (member == null) {
            throw  new UsernameNotFoundException("No Match Email");
        }
        MemberDTO memberDTO
                = new MemberDTO(member.getEmail(), member.getPassword(), member.getName() );
        return memberDTO;
    }
}
