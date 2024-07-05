package com.cicd.todomateapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoutineForm {
    private String detail;
    private LocalDate startDate;
    private LocalDate endDate;
}
