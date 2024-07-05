package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.Task;
import com.cicd.todomateapi.dto.TaskDTO;
import com.cicd.todomateapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    @Override
    public void add(TaskDTO taskDTO) {
        Task task = new Task().changeToTask(taskDTO);
        taskRepository.save(task);
    }

    @Override
    public Task get(Long tid) {
        return taskRepository.findById(tid).orElse(null);
    }

    @Override
    public void update(TaskDTO taskDTO) {
        Task task = new Task().changeToTask(taskDTO);
        taskRepository.save(task);
    }

    @Override
    public void delete(Long tid) {
        taskRepository.deleteById(tid);
    }

    @Override
    public List<Task> getNormalTaskList(LocalDate givenDate) {
        return taskRepository.getNormalTaskListByDate(givenDate);
    }

    @Override
    public List<Task> getRoutineTaskList(LocalDate givenDate) {
        return taskRepository.getRoutineTaskListByDate(givenDate);
    }

    @Override
    public void taskFinishedState(Long tid) {
        Task task = taskRepository.findById(tid).orElse(null);
        assert task != null;
        Task finishChange = task.finish();
        taskRepository.save(finishChange);

    }
}
