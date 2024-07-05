package com.cicd.todomateapi.service;

import com.cicd.todomateapi.dto.RoutineDTO;
import com.cicd.todomateapi.dto.RoutineForm;

import java.util.List;

public interface RoutineService {
    Long routineAdd(RoutineForm routineForm, Long mid);

    List<RoutineDTO> getRoutineMonthList(Long mid, int year, int month);

}
