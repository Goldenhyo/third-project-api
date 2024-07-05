package com.cicd.todomateapi.controller;

import com.cicd.todomateapi.domain.Task;
import com.cicd.todomateapi.dto.RoutineDTO;
import com.cicd.todomateapi.dto.RoutineForm;
import com.cicd.todomateapi.service.RoutineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    @PostMapping("/{mid}")
    public String add(@RequestBody RoutineForm routineForm, @PathVariable("mid") Long mid) {
        log.info("********** RoutineController add mid:{}, routineForm:{}", mid, routineForm);
        Long result = routineService.routineAdd(routineForm, mid);
        if (result > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @GetMapping("/{mid}/{year}/{month}")
    public Map<String, String> getRoutineList(@PathVariable("mid") Long mid,
                                              @PathVariable("year") int year,
                                              @PathVariable("month") int month){
        List<RoutineDTO> routineList = routineService.getRoutineMonthList(mid, year, month);
        log.info("************* TaskController.java / method name : getRoutineList / routineList : {}", routineList);
        return Map.of("RESULT", "");
    }
}
