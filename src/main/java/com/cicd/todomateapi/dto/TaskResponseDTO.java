package com.cicd.todomateapi.dto;

import com.cicd.todomateapi.domain.TaskType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class TaskResponseDTO {
    private Long tid;
    private String detail;
    private boolean finished;
    private TaskType type;
    private LocalDate date;
    private Long mid;
}
