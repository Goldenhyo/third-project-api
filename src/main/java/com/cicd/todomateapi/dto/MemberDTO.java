package com.cicd.todomateapi.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MemberDTO extends User {

    private String email;
    private String password;
    private String name;
    private Long mid;

    public MemberDTO(String email, String password,  String name, Long mid) {
        super(email, password, new ArrayList<>());
        this.email = email;
        this.password = password;
        this.name = name;
        this.mid = mid;
    }

    // 현재 사용자 정보를 Map 타입으로 리턴 (JWT 위한 메서드, 추후 JWT 문자열 생성시 사용)
    // MemberDTO 리턴시 User 포함하고 있어서 문제발생 가능 -> Map 타입으로 정보만 리턴
    public Map<String, Object> getClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("mid", mid);
        return map;
    }
}
