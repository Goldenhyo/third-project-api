package com.cicd.todomateapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DailyRoutineDTO {
    @Builder.Default
    private boolean finished = false;
    @Builder.Default
    private boolean deleted = false;
    private LocalDate date;
}
