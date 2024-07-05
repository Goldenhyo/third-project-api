package com.cicd.todomateapi.controller;

import com.cicd.todomateapi.dto.RoutineForm;
import com.cicd.todomateapi.service.RoutineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
}
