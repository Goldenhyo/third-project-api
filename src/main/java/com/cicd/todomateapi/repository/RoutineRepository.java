package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RoutineRepository extends JpaRepository<Routine, Long> {
    @Query("SELECT r FROM Routine r JOIN r.dailyList d WHERE d.drId = :drId AND r.mid = :mid")
    Routine getRoutineByDrId(@Param("mid") Long mid, @Param("drId") Long drId);

//    @Modifying
//    @Query("DELETE FROM Routine r WHERE r.mid = :mid")
    void deleteRoutinesByMid(@Param("mid") Long mid);
}
