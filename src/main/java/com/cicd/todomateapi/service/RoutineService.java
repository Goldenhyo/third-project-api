package com.cicd.todomateapi.service;

import com.cicd.todomateapi.dto.RoutineForm;

public interface RoutineService {
    Long routineAdd(RoutineForm routineForm, Long mid);

}
