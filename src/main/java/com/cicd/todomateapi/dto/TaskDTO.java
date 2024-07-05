package com.cicd.todomateapi.dto;

import com.cicd.todomateapi.domain.TaskType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private boolean finished;
    private String detail;
    private TaskType type;
    private LocalDate date;
    private Long mid;
}
