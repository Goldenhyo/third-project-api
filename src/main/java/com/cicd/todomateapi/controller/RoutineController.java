package com.cicd.todomateapi.controller;

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
    public String createRoutine(@RequestBody RoutineForm routineForm, @PathVariable("mid") Long mid) {
        log.info("********** RoutineController add mid:{}, routineForm:{}", mid, routineForm);
        Long result = routineService.createRoutine(routineForm, mid);
        return result > 0 ? "success" : "fail";
    }

    @GetMapping("/{mid}/{year}/{month}/{date}")
    public Map<String, List<RoutineDTO>> getRoutineList(@PathVariable("mid") Long mid,
                                              @PathVariable("year") int year,
                                              @PathVariable("month") int month,
                                              @PathVariable("date") int date){
        LocalDate givenDate = LocalDate.of(year, month, date);
        List<RoutineDTO> routineList = routineService.getDailyRoutineList(mid, givenDate);
        log.info("************* RoutineController getRoutineList / routineList : {}", routineList);
        return Map.of("RESULT", routineList);
    }

    @PutMapping("/daily/{drId}")
    public String updateDailyRoutineStatus(@PathVariable Long drId) {
        routineService.updateDailyRoutineStatus(drId);
        return "success";
    }

    @DeleteMapping("/daily/{drId}")
    public String deleteDailyRoutine(@PathVariable Long drId) {
        routineService.deleteDailyRoutine(drId);
        return "success";
    }

    @GetMapping("/{mid}/{drId}")
    public Map<String, RoutineDTO> getRoutine(@PathVariable("mid") Long mid, @PathVariable("drId") Long drId) {
        RoutineDTO routine = routineService.getRoutine(mid, drId);
        log.info("**************** RoutineController getRoutine routineDTO:{}", routine);
        return Map.of("RESULT", routine);
    }

    @PutMapping("/{rid}")
    public String updateRoutine(@PathVariable("rid") Long rid, @RequestBody RoutineForm routineForm) {
        Long l = routineService.updateRoutine(routineForm, rid);
        log.info("**************** RoutineController updateRoutine routineDTO:{}", l);
        return l > 0 ? "success" : "fail";
    }

    @DeleteMapping("/{rid}")
    public String deleteRoutine(@PathVariable("rid") Long rid) {
        routineService.deleteRoutine(rid);
        return "success";
    }
}
