package com.cicd.todomateapi.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @ElementCollection
    @Builder.Default
    private List<Long> friends = new ArrayList<>();
    @ElementCollection
    @Builder.Default
    private List<Long> request = new ArrayList<>();
}
