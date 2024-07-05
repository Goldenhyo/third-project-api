package com.cicd.todomateapi.controller;

import com.cicd.todomateapi.domain.Task;
import com.cicd.todomateapi.dto.TaskDTO;
import com.cicd.todomateapi.dto.TaskResponseDTO;
import com.cicd.todomateapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/add")
    public Map<String, String> addTask(@RequestBody TaskDTO taskDTO){
        taskService.add(taskDTO);
        log.info("************* TaskController.java / method name : addTask / taskDTO : {}", taskDTO);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/{tid}")
    public Map<String, TaskResponseDTO> getTask(@PathVariable Long tid){
        Task task = taskService.get(tid);
        log.info("************* TaskController.java / method name : getTask / task : {}", task);
        TaskResponseDTO taskDTO = task.changeToDTO();
        return Map.of("RESULT", taskDTO);
    }

    @PutMapping("/update/{tid}/{value}")
    public Map<String, String> updateTask(@PathVariable Long tid, @PathVariable String value){
        taskService.update(tid, value);
        log.info("************* TaskController.java / method name : updateTask");
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{tid}")
    public Map<String, String> deleteTask(@PathVariable Long tid){
        taskService.delete(tid);
        log.info("************* TaskController.java / method name : deleteTask / tid : {}", tid);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/normal/{year}/{month}/{date}")
    public Map<String, List<TaskResponseDTO>> getNormalTaskList(@PathVariable int year, @PathVariable int month, @PathVariable int date){
        LocalDate givenDate = LocalDate.of(year, month, date);
        List<Task> taskList = taskService.getNormalTaskList(givenDate);
        log.info("************* TaskController.java / method name : getTaskList / taskList : {}", taskList);
        List<TaskResponseDTO> taskDTOList = taskList.stream()
                .map(Task::changeToDTO)
                .toList();
        return Map.of("RESULT", taskDTOList);
    }

    @GetMapping("/routine/{year}/{month}/{date}")
    public Map<String, List<TaskResponseDTO>> getRoutineTaskList(@PathVariable int year, @PathVariable int month, @PathVariable int date){
        LocalDate givenDate = LocalDate.of(year, month, date);
        List<Task> taskList = taskService.getRoutineTaskList(givenDate);
        log.info("************* TaskController.java / method name : getTaskList / taskList : {}", taskList);
        List<TaskResponseDTO> taskDTOList = taskList.stream()
                .map(Task::changeToDTO)
                .toList();

        return Map.of("RESULT", taskDTOList);
    }

    @PostMapping("/{tid}")
    public Map<String, String> taskFinishedState(@PathVariable Long tid){
        taskService.taskFinishedState(tid);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/numoftask/{mid}/{year}/{month}")
    public Map<String, List<Integer>> getNumOfTask(@PathVariable Long mid, @PathVariable int year, @PathVariable int month){
        LocalDate givenDate = LocalDate.of(year, month, 1);
        List<Integer> numOfTask = taskService.numOfTask(mid, givenDate);
        return Map.of("RESULT", numOfTask);
    }
}
