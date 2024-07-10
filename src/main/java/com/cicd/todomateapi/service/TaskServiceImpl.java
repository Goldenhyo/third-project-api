package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.DailyRoutine;
import com.cicd.todomateapi.domain.Task;
import com.cicd.todomateapi.dto.TaskDTO;
import com.cicd.todomateapi.repository.DailyRoutineRepository;
import com.cicd.todomateapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final DailyRoutineRepository dailyRoutineRepository;

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
    public void update(Long tid, String value) {
        Task originTask = taskRepository.findById(tid).orElse(null);
        Task modifiedTask = originTask.modify(value);
        taskRepository.save(modifiedTask);
    }

    @Override
    public void delete(Long tid) {
        taskRepository.deleteById(tid);
    }

    @Override
    public List<Task> getNormalTaskList(Long mid, LocalDate givenDate) {
        return taskRepository.getNormalTaskListByDate(givenDate, mid);
    }

    @Override
    public void taskFinishedState(Long tid) {
        Task task = taskRepository.findById(tid).orElse(null);
        assert task != null;
        Task finishChange = task.finish();
        taskRepository.save(finishChange);

    }

    @Override
    public List<Integer> numOfTask(Long mid, LocalDate givenDate) {
        List<Task> taskList = taskRepository.getNumOfTask(mid, givenDate);
        List<DailyRoutine> routineList = dailyRoutineRepository.getNumOfDailyRoutine(mid, givenDate);
        int[] calcArray = new int[givenDate.lengthOfMonth()+1];
        int[] finishedArray = new int[givenDate.lengthOfMonth()+1];
        for (Task task : taskList) {
            int day = task.getDate().getDayOfMonth();
            if (task.isFinished()) {
                finishedArray[day]++;
            } else {
                calcArray[day]++;
            }
        }
        for(int i = 0; i< finishedArray.length; i++){
            if(finishedArray[i] != 0 && calcArray[i] == 0){
                calcArray[i] = -1;
            }
        }
        for (DailyRoutine routine : routineList) {
            int day = routine.getDate().getDayOfMonth();
            if (routine.isFinished()) {
                finishedArray[day]++;
            } else {
                calcArray[day]++;
            }
        }
        for(int i = 0; i< finishedArray.length; i++){
            if(finishedArray[i] != 0 && calcArray[i] == 0){
                calcArray[i] = -1;
            }
        }
        return Arrays.stream(calcArray).boxed().toList();
    }
}
