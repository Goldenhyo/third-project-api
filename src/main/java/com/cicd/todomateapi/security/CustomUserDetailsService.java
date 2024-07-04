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
public class CustomUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 이메일로 회원 조회하고 MemberDTO 리턴, 일치하는 이메일 없으면 예외 발생 시키기
        log.info("********** CustomUserDetailsService - loadUserByUsername - username:{}", username);
        Optional<Member> findMember = memberRepository.findMemberByEmail(username);
        if (findMember.isEmpty()) {
            throw new UsernameNotFoundException("No Match Email");
        } else {
            Member member = findMember.get();
            log.info("********** CustomUserDetailsService - loadUserByUsername - member:{}", member);
            MemberDTO memberDTO
                    = new MemberDTO(member.getEmail(), member.getPassword(), member.getName());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    memberDTO,
                    memberDTO.getPassword(),
                    memberDTO.getAuthorities()
            );
            log.info("********** CustomUserDetailsService - loadUserByUsername - authentication:{}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return memberDTO;
        }
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }
}
