package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    @Query("SELECT r FROM Routine r JOIN r.dailyList d WHERE d.drId = :drId AND r.mid = :mid")
    Routine getRoutineByDrId(@Param("mid") Long mid, @Param("drId") Long drId);
}
