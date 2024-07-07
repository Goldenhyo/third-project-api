package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.DailyRoutine;
import com.cicd.todomateapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyRoutineRepository extends JpaRepository<DailyRoutine, Long> {
    @Query("SELECT dr FROM DailyRoutine dr JOIN dr.routine r WHERE dr.date = :date AND r.mid = :mid ORDER BY dr.drId DESC")
    List<DailyRoutine> getDailyRoutineList(@Param("mid") Long mid, @Param("date") LocalDate date);

    @Query(value = "SELECT dr FROM DailyRoutine dr JOIN dr.routine r WHERE r.mid = :mid AND YEAR(dr.date) = YEAR(:givenDate) AND MONTH(dr.date) = MONTH(:givenDate)")
    List<DailyRoutine> getNumOfDailyRoutine(@Param("mid") Long mid, @Param("givenDate") LocalDate givenDate);
//    @Query(value = "SELECT * FROM daily_routine dr INNER JOIN routine r ON dr.routine_id = r.rid WHERE r.mid = :mid AND YEAR(dr.date) = YEAR(:givenDate) AND MONTH(dr.date) = MONTH(:givenDate)", nativeQuery = true)
//    List<DailyRoutine> getNumOfDailyRoutine(@Param("mid") Long mid, @Param("givenDate") LocalDate givenDate);
}
