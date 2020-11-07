package com.averude.uksatse.scheduler.controllers.interfaces;

import com.averude.uksatse.scheduler.core.dto.BasicDto;
import com.averude.uksatse.scheduler.core.dto.GenerationDTO;
import com.averude.uksatse.scheduler.core.entity.WorkingNorm;
import com.averude.uksatse.scheduler.core.entity.structure.Shift;
import com.averude.uksatse.scheduler.security.annotations.IsDepartmentAdmin;
import com.averude.uksatse.scheduler.security.annotations.IsDepartmentOrShiftAdmin;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/working_norm")
public interface WorkingNormController
        extends ICrudController<WorkingNorm>, IByAuthAndDateController<WorkingNorm> {

    @IsDepartmentOrShiftAdmin
    @RequestMapping(method = RequestMethod.GET, value = "/dto/dates")
    List<BasicDto<Shift, WorkingNorm>> getAllDtoByAuth(Authentication authentication,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam(value = "from")
                                                               LocalDate from,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam(value = "to")
                                                               LocalDate to);

    @IsDepartmentOrShiftAdmin
    @RequestMapping(method = RequestMethod.GET, value = "/dates")
    List<WorkingNorm> getAllByAuth(Authentication authentication,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @RequestParam(value = "from")
                                           LocalDate from,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @RequestParam(value = "to")
                                           LocalDate to);

    @IsDepartmentAdmin
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Optional<WorkingNorm> get(@PathVariable Long id);

    @IsDepartmentAdmin
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Long> post(@RequestBody WorkingNorm entity, Authentication authentication);

    @IsDepartmentAdmin
    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody WorkingNorm entity, Authentication authentication);

    @IsDepartmentAdmin
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

    @IsDepartmentAdmin
    @PostMapping(value = "/generate")
    void generateWorkingHoursNorm(Authentication authentication,
                                  @RequestBody @Valid GenerationDTO generationDTO);
}