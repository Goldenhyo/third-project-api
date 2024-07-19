package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.Member;
import com.cicd.todomateapi.dto.MemberDTO;
import com.cicd.todomateapi.dto.MemberForm;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MemberService {
    Member findById(Long mid);

    MemberDTO getKakaoMember(String accessToken);

    String save(MemberForm memberForm);

    String delete(Long mid);

    List<String> searchFriends(String searchFriends);

    Boolean friendRequest(Long bymid, String tomid);

    Boolean friendAccept(Long bymid, Long tomid, Boolean tf);

    List<Member> getFriend(Long mid);

    List<Member> getFriendRequest(Long mid);

    void friendBanned(Long bymid, Long tomid);
}
