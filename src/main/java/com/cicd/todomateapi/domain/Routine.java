package com.cicd.todomateapi.domain;

import com.cicd.todomateapi.dto.RoutineDTO;
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
    @GeneratedValue
    private Long rid; // routineId
    @Column
    private Long mid; // memberId
    private String detail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ElementCollection // 컬렉션 값 타입임을 명시: lazy 로딩이 default
    @Builder.Default // builder 패턴 사용하므로 반드시 부착해야함
    private List<RoutineList> dailyList = new ArrayList<>();

}
