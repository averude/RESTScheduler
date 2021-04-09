package com.averude.uksatse.scheduler.microservice.workschedule.controller;

import com.averude.uksatse.scheduler.core.model.entity.MainComposition;
import com.averude.uksatse.scheduler.security.annotations.IsEnterpriseOrDepartmentAdmin;
import com.averude.uksatse.scheduler.security.logging.Logged;
import com.averude.uksatse.scheduler.shared.service.MainCompositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/main_compositions")
@RestController
@RequiredArgsConstructor
public class MainCompositionController {

    private final MainCompositionService mainCompositionService;

    @Logged
    @PreAuthorize("@departmentLevelSecurity.hasPermission(authentication, 'MAP3', #mainComposition)")
    @PostMapping
    public MainComposition post(@Valid @RequestBody MainComposition mainComposition) {
        return mainCompositionService.save(mainComposition);
    }

    @Logged
    @PreAuthorize("@departmentLevelSecurity.hasPermission(authentication, 'MAP3', #mainComposition)")
    @PutMapping
    public MainComposition put(@Valid @RequestBody MainComposition mainComposition) {
        return mainCompositionService.save(mainComposition);
    }

    @Logged
    @IsEnterpriseOrDepartmentAdmin
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mainCompositionService.deleteById(id);
    }
}