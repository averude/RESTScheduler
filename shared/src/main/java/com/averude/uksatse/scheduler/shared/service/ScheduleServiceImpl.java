package com.averude.uksatse.scheduler.shared.service;

import com.averude.uksatse.scheduler.core.dto.BasicDto;
import com.averude.uksatse.scheduler.core.entity.Employee;
import com.averude.uksatse.scheduler.core.entity.MainShiftComposition;
import com.averude.uksatse.scheduler.core.entity.WorkDay;
import com.averude.uksatse.scheduler.core.interfaces.entity.EntityComposition;
import com.averude.uksatse.scheduler.shared.repository.EmployeeRepository;
import com.averude.uksatse.scheduler.shared.repository.MainShiftCompositionRepository;
import com.averude.uksatse.scheduler.shared.repository.ScheduleRepository;
import com.averude.uksatse.scheduler.shared.repository.SubstitutionShiftCompositionRepository;
import com.averude.uksatse.scheduler.shared.service.base.AService;
import com.averude.uksatse.scheduler.shared.utils.ScheduleDTOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    private final MainShiftCompositionRepository mainShiftCompositionRepository;
    private final SubstitutionShiftCompositionRepository substitutionShiftCompositionRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                               EmployeeRepository employeeRepository,
                               ScheduleDTOUtil scheduleDTOUtil,
                               MainShiftCompositionRepository mainShiftCompositionRepository,
                               SubstitutionShiftCompositionRepository substitutionShiftCompositionRepository) {
        super(scheduleRepository);
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.scheduleDTOUtil = scheduleDTOUtil;
        this.mainShiftCompositionRepository         = mainShiftCompositionRepository;
        this.substitutionShiftCompositionRepository = substitutionShiftCompositionRepository;
    }

    @Override
    @Transactional
    public List<? extends BasicDto<Employee, WorkDay>> findAllDtoByDepartmentIdAndDate(Long departmentId,
                                                                                       LocalDate from,
                                                                                       LocalDate to) {
        var employees = employeeRepository.findAllByDepartmentIdOrderById(departmentId);
        var mainShiftCompositions = mainShiftCompositionRepository.getAllByDepartmentIdAndDateBetweenOrdered(departmentId, from, to);
        var substitutionShiftCompositions = substitutionShiftCompositionRepository.getAllByDepartmentIdAndDateBetweenOrdered(departmentId, from, to);
        var schedule = scheduleRepository.findAllByDepartmentIdAndDateBetween(departmentId, from, to);

        return scheduleDTOUtil.createEmployeeScheduleDTOList(employees, mainShiftCompositions, substitutionShiftCompositions, schedule);
    }

    @Override
    @Transactional
    public List<? extends BasicDto<Employee, WorkDay>> findAllDtoByShiftIdAndDate(Long shiftId,
                                                                                  LocalDate from,
                                                                                  LocalDate to) {
        var mainShiftCompositions = mainShiftCompositionRepository
                .findAllByShiftIdAndToGreaterThanEqualAndFromLessThanEqual(shiftId, from, to);
        var mainShiftCompositionIds = mainShiftCompositions.stream().map(MainShiftComposition::getId).collect(toList());
        var substitutionShiftCompositions = substitutionShiftCompositionRepository
                .getAllByShiftIdAndMainShiftCompositionInAndDateBetweenOrdered(shiftId, mainShiftCompositionIds, from, to);

        // Later it will be optimized
        var employees = Stream.concat(mainShiftCompositions.stream(), substitutionShiftCompositions.stream())
                .map(EntityComposition::getSideB)
                .sorted(Comparator.comparingLong(Employee::getId))
                .distinct()
                .collect(toList());

        var departmentId = employees.get(0).getDepartmentId();


        var ids = employees.stream().map(Employee::getId).collect(toList());
        var schedule = scheduleRepository.findAllByEmployeeIdsAndDateBetween(departmentId, ids, from, to);

        return scheduleDTOUtil.createEmployeeScheduleDTOList(employees, mainShiftCompositions, substitutionShiftCompositions, schedule);
    }
}
