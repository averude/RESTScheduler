package com.averude.uksatse.scheduler.controllers.interfaces;

import com.averude.uksatse.scheduler.core.model.dto.BasicDto;
import com.averude.uksatse.scheduler.core.model.dto.GenerationDTO;
import com.averude.uksatse.scheduler.core.model.entity.WorkingNorm;
import com.averude.uksatse.scheduler.core.model.entity.structure.Shift;
import com.averude.uksatse.scheduler.security.annotations.IsDepartmentAdmin;
import com.averude.uksatse.scheduler.security.annotations.IsDepartmentOrShiftUser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/working_norm")
public interface WorkingNormController
        extends ICrudController<WorkingNorm>, IByAuthAndDateController<WorkingNorm> {

    @IsDepartmentOrShiftUser
    @GetMapping("/dto/dates")
    List<? extends BasicDto<Shift, WorkingNorm>> getAllDtoByAuth(Authentication authentication,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam(value = "from")
                                                               LocalDate from,
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       @RequestParam(value = "to")
                                                               LocalDate to);

    @PreAuthorize("@userPermissionChecker.checkDepartmentUser(authentication, #departmentId)")
    @GetMapping("/dto/departments/{departmentId}/dates")
    List<? extends BasicDto<Shift, WorkingNorm>> getAllDtoByDepartmentId(@PathVariable Long departmentId,
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 @RequestParam(value = "from")
                                                                         LocalDate from,
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 @RequestParam(value = "to")
                                                                         LocalDate to);

    @IsDepartmentOrShiftUser
    @GetMapping("/dates")
    List<WorkingNorm> getAllByAuth(Authentication authentication,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @RequestParam(value = "from")
                                           LocalDate from,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   @RequestParam(value = "to")
                                           LocalDate to);

    @PreAuthorize("@userPermissionChecker.checkDepartmentUser(authentication, #departmentId)")
    @GetMapping("/departments/{departmentId}/dates")
    List<WorkingNorm> getAllByDepartmentId(@PathVariable Long departmentId,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                           @RequestParam(value = "from")
                                                   LocalDate from,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                           @RequestParam(value = "to")
                                                   LocalDate to);

    @PreAuthorize("@userPermissionChecker.checkShiftUser(authentication, #shiftIds)")
    @GetMapping("/shifts/{shiftIds}/dates")
    List<WorkingNorm> getAllByShiftIds(@PathVariable List<Long> shiftIds,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       @RequestParam(value = "from")
                                               LocalDate from,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       @RequestParam(value = "to")
                                               LocalDate to);

    @IsDepartmentAdmin
    @GetMapping("/{id}")
    Optional<WorkingNorm> get(@PathVariable Long id);

    @IsDepartmentAdmin
    @PostMapping
    ResponseEntity<Long> post(@RequestBody WorkingNorm entity, Authentication authentication);

    @IsDepartmentAdmin
    @PutMapping
    ResponseEntity<?> put(@RequestBody WorkingNorm entity, Authentication authentication);

    @IsDepartmentAdmin
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication);

    @IsDepartmentAdmin
    @PostMapping(value = "/generate")
    void generateWorkingHoursNorm(Authentication authentication,
                                  @RequestBody @Valid GenerationDTO generationDTO);
}
