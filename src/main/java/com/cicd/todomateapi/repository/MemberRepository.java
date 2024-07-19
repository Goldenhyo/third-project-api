package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    @Query("SELECT m.name FROM Member m WHERE m.name LIKE :startwith%")
    List<String> findByNameStartingWith(@Param("startwith") String startwith);

    @Query("SELECT m FROM Member m WHERE m.name=:tomid")
    Member findMemberByName(@Param("tomid") String tomid);

}
