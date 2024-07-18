package com.cicd.todomateapi.domain;

import com.cicd.todomateapi.dto.TaskDTO;
import com.cicd.todomateapi.dto.TaskResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;
    private String detail;
    @Builder.Default
    private boolean finished = false;
    private TaskType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Column
    private Long mid;

    public Task changeToTask(TaskDTO taskDTO){
        this.detail = taskDTO.getDetail();
        this.finished = taskDTO.isFinished();
        this.type = taskDTO.getType();
        this.date = taskDTO.getDate();
        this.mid = taskDTO.getMid();
        return this;
    }

    public TaskResponseDTO changeToDTO(){
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setTid(this.getTid());
        taskResponseDTO.setDetail(this.getDetail());
        taskResponseDTO.setFinished(this.isFinished());
        taskResponseDTO.setType(this.getType());
        taskResponseDTO.setDate(this.getDate());
        taskResponseDTO.setMid(this.getMid());
        return taskResponseDTO;
    }

    public Task modify(String value) {
        this.detail = value;
        return this;
    }

    public Task finish(){
        this.finished = !finished;
        return this;
    }
}
