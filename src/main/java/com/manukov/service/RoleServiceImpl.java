package com.manukov.service;

import com.manukov.dao.RoleDao;
import com.manukov.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Transactional
    @Override
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

}
