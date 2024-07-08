package com.cicd.todomateapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drId; // daily routine Id;

    @Builder.Default
    private boolean finished = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;

    public DailyRoutine changeFinished(){
        this.finished = !finished;
        return this;
    }
}
