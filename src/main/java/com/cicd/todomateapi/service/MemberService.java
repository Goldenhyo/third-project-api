package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.Member;
import com.cicd.todomateapi.dto.MemberDTO;
import com.cicd.todomateapi.dto.MemberForm;

public interface MemberService {
    Member findById(Long mid);

    String save(MemberForm memberForm);

    String modify(MemberForm memberForm);

    String delete();
}
