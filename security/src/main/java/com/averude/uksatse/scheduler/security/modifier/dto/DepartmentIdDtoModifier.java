package com.averude.uksatse.scheduler.security.modifier.dto;

import com.averude.uksatse.scheduler.core.interfaces.entity.HasDepartmentId;
import com.averude.uksatse.scheduler.core.model.dto.BasicDto;
import com.averude.uksatse.scheduler.security.exception.NullOrgLevelIdException;
import com.averude.uksatse.scheduler.security.model.entity.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class DepartmentIdDtoModifier<P extends HasDepartmentId, C extends Serializable> implements DtoModifier<P, C> {
    @Override
    public void modify(BasicDto<P, C> dto, Authentication authentication) {
        var userAccount = (UserAccount) authentication.getPrincipal();
        var departmentId = userAccount.getDepartmentId();

        if (departmentId == null) throw new NullOrgLevelIdException();
        dto.getParent().setDepartmentId(departmentId);
    }
}
