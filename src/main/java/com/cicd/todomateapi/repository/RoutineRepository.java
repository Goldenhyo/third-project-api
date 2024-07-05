package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.Routine;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    // select 한번만 실행해 두개 테이블 내용 가져오기 위해 @EntityGraph 사용
    @Query(value = "SELECT * FROM routine " +
            "WHERE (EXTRACT(YEAR FROM start_date) = :year " +
            "AND EXTRACT(MONTH FROM start_date) = :month " +
            "AND mid = :mid ) " +
            "OR (EXTRACT(YEAR FROM end_date) = :year " +
            "AND EXTRACT(MONTH FROM end_date) = :month)" +
            "AND mid = :mid", nativeQuery = true)
    List<Routine> findRoutinesMonth(@Param("mid") Long mid, @Param("year") int year, @Param("month") int month);
}
