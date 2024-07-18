package com.cicd.todomateapi.domain;

import com.cicd.todomateapi.dto.RoutineForm;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid; // routineId
    private Long mid; // memberId
    private String detail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "routine")
    @Builder.Default
    private List<DailyRoutine> dailyList = new ArrayList<>();

    public Routine updateRoutine(RoutineForm routineForm) {
        this.detail = routineForm.getDetail();
        this.startDate = routineForm.getStartDate();
        this.endDate = routineForm.getEndDate();
        this.getDailyList().clear();
        for (LocalDate date = routineForm.getStartDate();
             !date.isAfter(routineForm.getEndDate());
             date = date.plusDays(1)) {
            DailyRoutine dailyRoutine = DailyRoutine.builder()
                    .date(date)
                    .routine(this)
                    .build();
            this.dailyList.add(dailyRoutine);
        }
        return this;
    }
}
