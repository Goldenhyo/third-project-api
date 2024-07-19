package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.Member;
import com.cicd.todomateapi.dto.MemberDTO;
import com.cicd.todomateapi.dto.MemberForm;
import com.cicd.todomateapi.repository.MemberRepository;
import com.cicd.todomateapi.repository.RoutineRepository;
import com.cicd.todomateapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoutineRepository routineRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member findById(Long mid) {
        return memberRepository.findById(mid)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Override
    public MemberDTO getKakaoMember(String accessToken) {
        log.info("*********** MemberService - getKakaoMember accessToken:{}", accessToken);
        String email = getEmailFromKakaoAccessToken(accessToken);
        log.info("********** MemberService - getKakaoMember email:{}", email);
        // DB에 회원이 있는지 조회
        Optional<Member> findMember = memberRepository.findMemberByEmail(email);
        // 기존회원 -> 로그인처리
        if (findMember.isPresent()) {
            log.info("*********** 회원임");
            return entityToDTO(findMember.get());
        }
        // 회원이 아닌 경우 -> 회원 추가
        // 임시비번으로 회원 DB에 추가, 해당 정보로 memberDTO 리턴
        log.info("*********** 비회원임");
        Member socialMember = makeSocialMember(email); // 소셜회원으로 만들어 받기
        Member saved = memberRepository.save(socialMember);// DB에 저장
        log.info("****************** MemberService saved.getEmail:{}", saved.getEmail());
        return entityToDTO(saved);
    }

    // 소셜회원 MemberEntity 만들어주는 메서드
    private Member makeSocialMember(String email) {
        // 임시비번 만들어서 MemberEntity 생성해서 리턴
        String tmpPassword = makeTemPassword();
        log.info("********** MemberServiceImpl makeSocialMember - tmpPassword :{}", tmpPassword);
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(tmpPassword))
                .build();
        return member;
    }

    // 임시비번 만들어주는 메서드
    private String makeTemPassword() {
        // char 한글자씩 랜덤으로 누적 추가해서 문자열 만들어주기
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append((char) ((int) (Math.random() * 55) + 65));
        }
        return stringBuffer.toString();
    }

    // 카카오 사용자 정보 요청
    private String getEmailFromKakaoAccessToken(String accessToken) {
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";
        if (accessToken == null) {
            throw new RuntimeException("Access Token is null");
        }
        // * 카카오서버에 RestTemplate 으로 사용자 정보 HTTP 요청
        RestTemplate restTemplate = new RestTemplate();
        // 헤더정보 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // 헤더 정보를 포함한 HttpEntity 로 요청객체 만들기 (request)
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // 요청 경로 생성해주는 클래스 이용
        UriComponents uriBuild = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();
        // RestTemplate 의 exchange() 메서드를 이용해 요청보내기, 리턴은 LinkedHashMap 받기
        ResponseEntity<LinkedHashMap> response
                = restTemplate.exchange(uriBuild.toString(), HttpMethod.GET, entity, LinkedHashMap.class);
        log.info("********** MemberService - response:{}", response);
        // Body 에서 응답 데이터 꺼내기
        LinkedHashMap<String, LinkedHashMap> body = response.getBody();
        log.info("********** MemberService - body:{}", body);
        // 응답 내용 중 카카오 계정 정보 꺼내기
        LinkedHashMap<String, String> kakaoAccount = body.get("kakao_account");
        log.info("********** MemberService - kakaoAccount:{}", kakaoAccount);
        return kakaoAccount.get("email"); // 이메일만 꺼내서 리턴
    }

    public MemberDTO entityToDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getMid());
        return memberDTO;
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
    public String delete(Long mid) {
        taskRepository.deleteTasksByMid(mid);
        routineRepository.deleteRoutinesByMid(mid);
        memberRepository.deleteById(mid);
        return "success";
    }

    @Override
    public List<String> searchFriends(String searchFriends) {
        return memberRepository.findByNameStartingWith(searchFriends);
    }

    @Override // 친구 요청
    public Boolean friendRequest(Long bymid, String tomid) {
        Member toMember = memberRepository.findMemberByName(tomid);
        toMember.getRequest().add(bymid);
        memberRepository.save(toMember);
        return true;
    }
    @Override // 친구 요청
    public Boolean friendAccept(Long bymid, Long tomid, Boolean tf) {
        log.info("************* MemberServiceImpl.java / method name : friendAccept / tf : {}", tf);
        Optional<Member> toMember = memberRepository.findById(tomid);
        if(toMember.isPresent()){
            Member toPresentMember = toMember.get();
            toPresentMember.getRequest().removeIf(r -> Objects.equals(r, bymid));
            if(tf) {
                log.info("************* aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                toPresentMember.getFriends().add(bymid);
                Member byPresentMember = memberRepository.findById(bymid).get();
                byPresentMember.getFriends().add(tomid);
                memberRepository.save(byPresentMember);
            }
            memberRepository.save(toPresentMember);
            return true;
        }
        return false;
    }

    @Override
    public List<Member> getFriend(Long mid) {
        List<Long> longArr = memberRepository.findById(mid).get().getFriends();
        return longArr.stream().map(a -> memberRepository.findById(a).orElse(null)).toList();
    }

    @Override
    public List<Member> getFriendRequest(Long mid) {
        List<Long> longArr = memberRepository.findById(mid).get().getRequest();
        return longArr.stream().map(a -> memberRepository.findById(a).orElse(null)).toList();
    }

    @Override
    public void friendBanned(Long bymid, Long tomid) {
        Member member = memberRepository.findById(bymid).orElse(null);
        member.getFriends().removeIf(m -> m.equals(tomid));
    }

}
