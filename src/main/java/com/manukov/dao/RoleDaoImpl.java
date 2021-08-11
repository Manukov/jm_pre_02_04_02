package com.manukov.dao;

import com.manukov.entity.Role;
import com.manukov.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getRoles() {
        TypedQuery<Role> query = entityManager.createQuery("from Role", Role.class);
        List<Role> roles = query.getResultList();
        return roles;
    }

    @Override
    public Role getRoleById(long id) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT role FROM Role role WHERE role.id=:id", Role.class);
        Role role =query.setParameter("id", id).getSingleResult();
        return role;
    }

    @Override
    public Set<Role> getRolesById(long[] ids) {

        Set<Role> roles = new HashSet<>();

        for (int i=0; i<ids.length; i++) {
            roles.add(getRoleById(ids[i]));
        }
        return roles;
    }
}
