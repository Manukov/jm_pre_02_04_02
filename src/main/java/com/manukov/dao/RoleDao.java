package com.manukov.dao;

import com.manukov.entity.Role;;
import com.manukov.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    List<Role> getRoles();
    Role getRoleById(long id);
    Set<Role> getRolesById(long[] ids);
}
