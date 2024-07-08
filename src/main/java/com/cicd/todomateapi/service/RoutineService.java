package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.DailyRoutine;
import com.cicd.todomateapi.domain.Routine;
import com.cicd.todomateapi.dto.RoutineDTO;
import com.cicd.todomateapi.dto.RoutineForm;

import java.time.LocalDate;
import java.util.List;

public interface RoutineService {
    Long createRoutine(RoutineForm routineForm, Long mid);

    void deleteRoutine(Long rid);

    Long updateRoutine(RoutineForm routineForm, Long rid);

    RoutineDTO getRoutine(Long mid, Long drId);

    // Daily Routine
    List<RoutineDTO> getDailyRoutineList(Long mid, LocalDate givenDate);

    void deleteDailyRoutine(Long drId);

    void updateDailyRoutineStatus(Long drId);
}
