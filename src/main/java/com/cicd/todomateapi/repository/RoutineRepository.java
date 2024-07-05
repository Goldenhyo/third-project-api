package com.cicd.todomateapi.repository;

import com.cicd.todomateapi.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
}
