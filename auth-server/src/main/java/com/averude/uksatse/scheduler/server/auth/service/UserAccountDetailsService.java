package com.averude.uksatse.scheduler.server.auth.service;

import com.averude.uksatse.scheduler.core.interfaces.service.IByDepartmentIdService;
import com.averude.uksatse.scheduler.core.interfaces.service.IByEnterpriseIdService;
import com.averude.uksatse.scheduler.core.interfaces.service.IService;
import com.averude.uksatse.scheduler.security.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountDetailsService extends UserDetailsService,
        IByEnterpriseIdService<UserAccount, Long>,
        IByDepartmentIdService<UserAccount, Long>,
        IService<UserAccount, Long> {
}