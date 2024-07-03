package com.cicd.todomateapi.dto;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

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
