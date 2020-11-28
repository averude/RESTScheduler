package com.averude.uksatse.scheduler.statistics.service;

import com.averude.uksatse.scheduler.core.dto.SummationDTO;
import com.averude.uksatse.scheduler.shared.repository.SpecialCalendarDateRepository;
import com.averude.uksatse.scheduler.shared.repository.SummationColumnRepository;
import com.averude.uksatse.scheduler.shared.service.ScheduleService;
import com.averude.uksatse.scheduler.statistics.calculator.StatisticsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummationDTOServiceImpl implements SummationDTOService {

    private final StatisticsCalculator          statisticsCalculator;
    private final SummationColumnRepository     summationColumnRepository;
    private final ScheduleService               scheduleService;
    private final SpecialCalendarDateRepository specialCalendarDateRepository;

    @Autowired
    public SummationDTOServiceImpl(StatisticsCalculator statisticsCalculator,
                                   SummationColumnRepository summationColumnRepository,
                                   ScheduleService scheduleService,
                                   SpecialCalendarDateRepository specialCalendarDateRepository) {
        this.statisticsCalculator = statisticsCalculator;
        this.summationColumnRepository = summationColumnRepository;
        this.scheduleService = scheduleService;
        this.specialCalendarDateRepository = specialCalendarDateRepository;
    }

    @Override
    @Transactional
    public List<SummationDTO> findAllByDepartmentIdAndDateBetween(Long departmentId,
                                                                  LocalDate from,
                                                                  LocalDate to) {
        var specialCalendarDates = specialCalendarDateRepository.findAllByDepartmentIdAndDateBetween(departmentId, from, to);
        var summationColumns = summationColumnRepository.findAllByDepartmentId(departmentId);
        if (summationColumns == null || summationColumns.size() == 0) {
            return null;
        }
        return scheduleService.findAllDtoByDepartmentIdAndDate(departmentId, from, to)
                .stream()
                .map(scheduleDTO -> new SummationDTO(scheduleDTO.getParent(), from, to,
                        statisticsCalculator.calculate(summationColumns, scheduleDTO.getCollection(), specialCalendarDates)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SummationDTO> findAllByShiftIdAndDateBetween(Long shiftId, LocalDate from, LocalDate to) {
        var specialCalendarDates = specialCalendarDateRepository.findAllByShiftIdAndDateBetween(shiftId, from, to);
        var summationColumns = summationColumnRepository.findAllByShiftId(shiftId);
        if (summationColumns == null || summationColumns.size() == 0) {
            return null;
        }
        return scheduleService.findAllDtoByShiftIdAndDate(shiftId, from, to)
                .stream()
                .map(scheduleDTO -> new SummationDTO(scheduleDTO.getParent(), from, to,
                        statisticsCalculator.calculate(summationColumns, scheduleDTO.getCollection(), specialCalendarDates)))
                .collect(Collectors.toList());
    }
}