package com.averude.uksatse.scheduler.core.service;

import com.averude.uksatse.scheduler.core.dto.BasicDto;
import com.averude.uksatse.scheduler.core.validation.CheckDateParameters;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public interface IByDepartmentIdAndDateDtoService<E extends Serializable, C extends Serializable, ID> {
    @CheckDateParameters
    List<BasicDto<E, C>> findAllDtoByDepartmentIdAndDate(Long departmentId,
                                                         LocalDate from,
                                                         LocalDate to);
}
