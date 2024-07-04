package com.cicd.todomateapi.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@Slf4j
public class MemberDTO extends User {

    private String email;
    private String password;
    private String name;

    public MemberDTO(String email, String password,  String name) {
        super(email, password, new ArrayList<>());
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
