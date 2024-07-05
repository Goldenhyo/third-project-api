package com.cicd.todomateapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable // 값 타입을 명시해주는 어노테이션
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyRoutine {
    @Builder.Default
    private boolean finished = false;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Builder.Default
    private boolean deleted = false;
}
