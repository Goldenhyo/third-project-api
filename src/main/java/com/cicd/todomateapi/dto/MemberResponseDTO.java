package com.cicd.todomateapi.dto;

import com.cicd.todomateapi.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MemberResponseDTO {
    private Long mid;
    private String email;
    private String name;

    public MemberResponseDTO(Member member){
        this.mid = member.getMid();
        this.email = member.getEmail();
        this.name = member.getName();
    }
}
