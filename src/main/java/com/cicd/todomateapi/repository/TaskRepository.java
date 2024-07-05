package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT * FROM Task WHERE date = :givenDate AND type = 0 ORDER BY tid DESC", nativeQuery = true)
    List<Task> getNormalTaskListByDate(@Param("givenDate")LocalDate givenDate);

    @Query(value = "SELECT * FROM Task WHERE date = :givenDate AND type = 1 ORDER BY tid DESC", nativeQuery = true)
    List<Task> getRoutineTaskListByDate(@Param("givenDate")LocalDate givenDate);

    @Query(value = "SELECT * FROM Task WHERE mid = :mid AND YEAR(date) = YEAR(:givenDate) AND MONTH(date) = MONTH(:givenDate)", nativeQuery = true)
    List<Task> getNumOfTask(@Param("mid") Long mid, @Param("givenDate") LocalDate givenDate);

}
