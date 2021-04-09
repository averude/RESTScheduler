package com.averude.uksatse.scheduler.shared.service;

import com.averude.uksatse.scheduler.core.interfaces.entity.Composition;
import com.averude.uksatse.scheduler.core.model.dto.EmployeeScheduleDTO;
import com.averude.uksatse.scheduler.core.model.entity.Employee;
import com.averude.uksatse.scheduler.core.model.entity.MainComposition;
import com.averude.uksatse.scheduler.core.model.entity.SubstitutionComposition;
import com.averude.uksatse.scheduler.core.model.entity.WorkDay;
import com.averude.uksatse.scheduler.shared.repository.MainCompositionRepository;
import com.averude.uksatse.scheduler.shared.repository.ScheduleRepository;
import com.averude.uksatse.scheduler.shared.repository.SubstitutionCompositionRepository;
import com.averude.uksatse.scheduler.shared.repository.common.EmployeeRepository;
import com.averude.uksatse.scheduler.shared.service.base.AService;
import com.averude.uksatse.scheduler.shared.utils.ScheduleDTOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class ScheduleServiceImpl
        extends AService<WorkDay, Long> implements ScheduleService {

    private final ScheduleRepository    scheduleRepository;
    private final EmployeeRepository    employeeRepository;
    private final ScheduleDTOUtil       scheduleDTOUtil;

    private final MainCompositionRepository         mainCompositionRepository;
    private final SubstitutionCompositionRepository substitutionCompositionRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                               EmployeeRepository employeeRepository,
                               ScheduleDTOUtil scheduleDTOUtil,
                               MainCompositionRepository mainCompositionRepository,
                               SubstitutionCompositionRepository substitutionCompositionRepository) {
        super(scheduleRepository);
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.scheduleDTOUtil = scheduleDTOUtil;
        this.mainCompositionRepository = mainCompositionRepository;
        this.substitutionCompositionRepository = substitutionCompositionRepository;
    }

    @Override
    @Transactional
    public List<EmployeeScheduleDTO> findAllDtoByDepartmentIdAndDate(Long departmentId,
                                                                     LocalDate from,
                                                                     LocalDate to) {
        var employees = employeeRepository.findAllByDepartmentIdOrderById(departmentId);
        var mainShiftCompositions = mainCompositionRepository.getAllByDepartmentIdAndDateBetweenOrdered(departmentId, from, to);
        var substitutionShiftCompositions = substitutionCompositionRepository.getAllByDepartmentIdAndDateBetweenOrdered(departmentId, from, to);
        var schedule = scheduleRepository.findAllByDepartmentIdAndDateBetween(departmentId, from, to);

        return scheduleDTOUtil.createEmployeeScheduleDTOList(employees, mainShiftCompositions, substitutionShiftCompositions, schedule);
    }

    @Override
    @Transactional
    public List<EmployeeScheduleDTO> findScheduleDTOByShiftIdsAndDate(List<Long> shiftIds,
                                                                      LocalDate from,
                                                                      LocalDate to) {
        var mainCompositions = mainCompositionRepository
                .getAllByShiftIdsAndDateBetweenOrdered(shiftIds, from, to);
        var mainCompositionIds = mainCompositions.stream().map(MainComposition::getId).collect(toList());

        var subCompositions = getSubstitutionCompositions(shiftIds, mainCompositionIds, from, to);

        if (mainCompositions.isEmpty() && subCompositions.isEmpty()) {
            return Collections.emptyList();
        }

        var employeeIds = Stream.concat(mainCompositions.stream(), subCompositions.stream())
                .map(Composition::getEmployeeId)
                .sorted()
                .distinct()
                .collect(toList());

        var employees = employeeRepository.findAllById(employeeIds);
        employees.sort(Comparator.comparing(Employee::getId));

        var departmentId = employees.get(0).getDepartmentId();

        var schedule = scheduleRepository.findAllByEmployeeIdsAndDateBetween(departmentId, employeeIds, from, to);

        return scheduleDTOUtil.createEmployeeScheduleDTOList(employees, mainCompositions, subCompositions, schedule);
    }

    private List<SubstitutionComposition> getSubstitutionCompositions(List<Long> shiftIds,
                                                                      List<Long> mainCompositionIds,
                                                                      LocalDate from,
                                                                      LocalDate to) {
        if (mainCompositionIds == null || mainCompositionIds.isEmpty()) {
            return substitutionCompositionRepository.findAllByShiftIdsAndDatesBetween(shiftIds, from, to);
        } else {
            return substitutionCompositionRepository
                    .getAllByShiftIdsAndMainShiftCompositionInAndDateBetweenOrdered(shiftIds, mainCompositionIds, from, to);
        }
    }
}
