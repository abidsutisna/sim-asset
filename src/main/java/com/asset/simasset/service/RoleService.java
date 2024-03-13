package com.asset.simasset.service;

import com.asset.simasset.entity.Role;
import com.asset.simasset.utils.RoleEnum;

public interface RoleService {
    Role getOrSave(RoleEnum role);
}
