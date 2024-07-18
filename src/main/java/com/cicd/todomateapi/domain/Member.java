package com.cicd.todomateapi.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String name;
}
