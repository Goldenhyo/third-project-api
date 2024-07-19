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

    List<String> searchFriends(Long mid, String searchFriends);

    Boolean friendRequest(Long bymid, String tomid);

    List<Member> getFriend(Long mid);

    List<Member> getFriendRequest(Long mid);

    Boolean friendAccept(Long bymid, Long tomid, Boolean tf);

    void friendBanned(Long bymid, Long tomid);
}
