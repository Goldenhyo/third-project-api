package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.date = :givenDate AND t.type = 0 AND t.mid = :mid ORDER BY t.tid DESC")
    List<Task> getNormalTaskListByDate(@Param("givenDate") LocalDate givenDate, @Param("mid") Long mid);

    @Query("SELECT t FROM Task t WHERE t.mid = :mid AND FUNCTION('YEAR', t.date) = FUNCTION('YEAR', :givenDate) AND FUNCTION('MONTH', t.date) = FUNCTION('MONTH', :givenDate)")
    List<Task> getNumOfTask(@Param("mid") Long mid, @Param("givenDate") LocalDate givenDate);

//    @Modifying
//    @Query("DELETE FROM Task t WHERE t.mid = :mid")
    void deleteTasksByMid(@Param("mid") Long mid);
}
