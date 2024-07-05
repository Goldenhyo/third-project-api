package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.Routine;
import com.cicd.todomateapi.dto.RoutineDTO;
import com.cicd.todomateapi.dto.RoutineForm;
import com.cicd.todomateapi.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService{

    private final RoutineRepository routineRepository;

    @Override
    public Long routineAdd(RoutineForm routineForm, Long mid) {
        log.info("********** RoutineService add");
        Routine routine = Routine.builder()
                .mid(mid)
                .detail(routineForm.getDetail())
                .startDate(routineForm.getStartDate())
                .endDate(routineForm.getEndDate())
                .build();
        Routine saved = routineRepository.save(routine);
        Long rid = saved.getRid();
        return rid;
    }

    @Override
    public List<RoutineDTO> getRoutineMonthList(Long mid, int year, int month) {
        List<Routine> routines = routineRepository.findRoutinesMonth(mid, year, month);
        List<RoutineDTO> routineDTOList = new ArrayList<>();
        for (Routine routine : routines) {
            RoutineDTO routineDTO = RoutineDTO.builder()
                    .rid(routine.getRid())
                    .detail(routine.getDetail())
                    .startDate(routine.getStartDate())
                    .endDate(routine.getEndDate())
                    .build();
            routineDTOList.add(routineDTO);
        }
        log.info("********** RoutineServiceImpl getRoutineMonthList routineDTO:{}", routineDTOList);
        return routineDTOList;
    }
}
