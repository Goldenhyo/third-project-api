package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.Task;
import com.cicd.todomateapi.dto.TaskDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TaskService {
    void add(TaskDTO taskDTO);
    Task get(Long tid);
    void update(Long tid, String value);
    void delete(Long tid);
    List<Task> getNormalTaskList(LocalDate givenDate);
    List<Task> getRoutineTaskList(LocalDate givenDate);
    void taskFinishedState(Long tid);
    List<Integer> numOfTask(Long mid, LocalDate givenDate);
}
