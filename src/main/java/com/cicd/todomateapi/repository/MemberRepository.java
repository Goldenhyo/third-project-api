package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
