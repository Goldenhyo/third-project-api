package com.cicd.todomateapi.controller;

import com.cicd.todomateapi.domain.Task;
import com.cicd.todomateapi.dto.TaskDTO;
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
    public Map<String, Task> getTask(@PathVariable Long tid){
        Task task = taskService.get(tid);
        log.info("************* TaskController.java / method name : getTask / task : {}", task);
        return Map.of("RESULT", task);
    }

    @PutMapping("/update")
    public Map<String, String> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        log.info("************* TaskController.java / method name : updateTask / taskDTO : {}", taskDTO);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{tid}")
    public Map<String, String> deleteTask(@PathVariable Long tid){
        taskService.delete(tid);
        log.info("************* TaskController.java / method name : deleteTask / tid : {}", tid);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/normal/{year}/{month}/{date}")
    public Map<String, List<Task>> getNormalTaskList(@PathVariable int year, @PathVariable int month, @PathVariable int date){
        LocalDate givenDate = LocalDate.of(year, month, date);
        List<Task> taskList = taskService.getNormalTaskList(givenDate);
        log.info("************* TaskController.java / method name : getTaskList / taskList : {}", taskList);
        return Map.of("RESULT", taskList);
    }

    @GetMapping("/routine/{year}/{month}/{date}")
    public Map<String, List<Task>> getRoutineTaskList(@PathVariable int year, @PathVariable int month, @PathVariable int date){
        LocalDate givenDate = LocalDate.of(year, month, date);
        List<Task> taskList = taskService.getRoutineTaskList(givenDate);
        log.info("************* TaskController.java / method name : getTaskList / taskList : {}", taskList);
        return Map.of("RESULT", taskList);
    }

    @PostMapping("/{tid}")
    public Map<String, String> taskFinishedState(@PathVariable Long tid){
        taskService.taskFinishedState(tid);
        return Map.of("RESULT", "SUCCESS");
    }
}
