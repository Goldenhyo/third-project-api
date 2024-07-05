package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.Member;
import com.cicd.todomateapi.dto.MemberForm;
import com.cicd.todomateapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member findById(Long mid) {
        return memberRepository.findById(mid)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Override
    public String save(MemberForm memberForm) {
        Optional<Member> findMember = memberRepository.findMemberByEmail(memberForm.getEmail());
        if (findMember.isPresent()) {
            log.info("*********MemberServiceImpl save result: exist");
            return "exist";
        } else {
            Member member = Member.builder()
                    .email(memberForm.getEmail())
                    .password(passwordEncoder.encode(memberForm.getPassword()))
                    .name(memberForm.getName())
                    .build();
            memberRepository.save(member);
            log.info("*********MemberServiceImpl save result: success");
            return "success";
        }
    }

    @Override
    public String modify(MemberForm memberForm) {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }
}
