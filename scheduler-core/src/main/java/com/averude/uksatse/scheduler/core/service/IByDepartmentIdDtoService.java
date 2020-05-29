package com.averude.uksatse.scheduler.core.service;

import com.averude.uksatse.scheduler.core.dto.BasicDto;

import java.io.Serializable;
import java.util.List;

public interface IByDepartmentIdDtoService<T extends Serializable, C extends Serializable, ID> {
    List<BasicDto<T, C>> findAllDtoByDepartmentId(Long departmentId);
    BasicDto<T, C> saveDto(BasicDto<T, C> dto);
}
