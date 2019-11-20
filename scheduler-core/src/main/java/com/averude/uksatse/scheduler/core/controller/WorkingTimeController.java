package com.averude.uksatse.scheduler.core.controller;

import com.averude.uksatse.scheduler.core.entity.WorkingTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/working_time")
public interface WorkingTimeController extends BasicCrudController<WorkingTime> {

    @RequestMapping(method = RequestMethod.GET,
                    value = "/departments/{departmentId}/dates")
    List<WorkingTime> getAllByDepartmentIdAndDateBetween(@PathVariable
                                                                 Long departmentId,
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                         @RequestParam(value = "from")
                                                                 LocalDate from,
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                         @RequestParam(value = "to")
                                                                 LocalDate to);

    @RequestMapping(method = RequestMethod.GET,
                    value = "/shifts/{shiftId}/dates")
    List<WorkingTime> getAllByShiftIdAndDateBetween(@PathVariable
                                                            Long shiftId,
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                    @RequestParam(value = "from")
                                                            LocalDate from,
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                    @RequestParam(value = "to")
                                                            LocalDate to);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Optional<WorkingTime> get(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Long> post(@Valid @RequestBody WorkingTime entity);

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> put(@Valid @RequestBody WorkingTime entity);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);
}
