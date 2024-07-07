package com.cicd.todomateapi.service;

import com.cicd.todomateapi.domain.DailyRoutine;
import com.cicd.todomateapi.domain.Routine;
import com.cicd.todomateapi.dto.RoutineDTO;
import com.cicd.todomateapi.dto.RoutineForm;
import com.cicd.todomateapi.repository.DailyRoutineRepository;
import com.cicd.todomateapi.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService{

    private final RoutineRepository routineRepository;
    private final DailyRoutineRepository dailyRoutineRepository;

    @Override
    public Long createRoutine(RoutineForm routineForm, Long mid) {
        log.info("********** RoutineService createRoutine");
        Routine routine = Routine.builder()
                .mid(mid)
                .detail(routineForm.getDetail())
                .startDate(routineForm.getStartDate())
                .endDate(routineForm.getEndDate())
                .build();
        for (LocalDate date = routineForm.getStartDate();
             !date.isAfter(routineForm.getEndDate());
             date = date.plusDays(1)) {
            DailyRoutine dailyRoutine = DailyRoutine.builder()
                    .date(date)
                    .routine(routine)
                    .build();
            routine.getDailyList().add(dailyRoutine);
        }
        Routine saved = routineRepository.save(routine);
        Long rid = saved.getRid();
        return rid;
    }

    @Override
    public void deleteRoutine(Long rid) {
        routineRepository.deleteById(rid);
    }

    @Override
    public Long updateRoutine(RoutineForm routineForm, Long rid) {
        Routine saved = routineRepository.findById(rid).orElse(null);
        return saved.updateRoutine(routineForm).getRid();
    }

    @Override
    public RoutineDTO getRoutine(Long mid, Long drId) {
        Routine routine = routineRepository.getRoutineByDrId(mid, drId);
        RoutineDTO routineDTO = RoutineDTO.builder()
                .rid(routine.getRid())
                .detail(routine.getDetail())
                .startDate(routine.getStartDate())
                .endDate(routine.getEndDate())
                .build();
        return routineDTO;
    }

    @Override
    public List<RoutineDTO> getDailyRoutineList(Long mid, LocalDate givenDate) {
        List<DailyRoutine> routinesByDate = dailyRoutineRepository.getDailyRoutineList(mid, givenDate);
        List<RoutineDTO> result = new ArrayList<>();
        for (DailyRoutine dailyRoutine : routinesByDate) {
            RoutineDTO routineDTO = RoutineDTO.builder()
                    .rid(dailyRoutine.getRoutine().getRid())
                    .drId(dailyRoutine.getDrId())
                    .startDate(dailyRoutine.getRoutine().getStartDate())
                    .endDate(dailyRoutine.getRoutine().getEndDate())
                    .date(dailyRoutine.getDate())
                    .detail(dailyRoutine.getRoutine().getDetail())
                    .finished(dailyRoutine.isFinished())
                    .build();
            result.add(routineDTO);
        }
        log.info("*************** RoutineController getDailyRoutineList result:{}", result);
        return result;
    }

    @Override
    public void deleteDailyRoutine(Long drId) {
        dailyRoutineRepository.deleteById(drId);
    }

    @Override
    public void updateDailyRoutineStatus(Long drId) {
        DailyRoutine dailyRoutine = dailyRoutineRepository.findById(drId).orElseThrow();
        dailyRoutine.changeFinished();
        dailyRoutineRepository.save(dailyRoutine);
    }
}
