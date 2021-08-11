package com.manukov.dao;

import com.manukov.entity.Role;
import com.manukov.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    private RoleDao roleDao;

    public UserDaoImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user WHERE user.username=:username", User.class);
        User user = query.setParameter("username", username).getSingleResult();
        return user;
    }

    @Override
    public List<User> getUsers() {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public boolean addUser(User user, String[] ids) {
        Set<Role> roles = roleDao.getRolesById(StringIdToLongId(ids));
        user.setRoles(roles);

        try {
            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findById(long id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user WHERE user.id=:id", User.class);
        User user = query.setParameter("id", id).getSingleResult();
        return user;
    }

    @Override
    public boolean updateUser(User user, String[] roles) {

        User u = findById(user.getId());

        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        Set<Role> rl = roleDao.getRolesById(StringIdToLongId(roles));
        u.setRoles(rl);

        try {
            entityManager.persist(u);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(long id) {
        entityManager.createQuery("DELETE FROM User WHERE id=:id");

        try {
            entityManager.createQuery("DELETE FROM User WHERE id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private long[] StringIdToLongId (String[] rolesId) {
        int counter = rolesId.length;
        long[] id = new long[counter];
        for (int i=0; i<counter; i++) {
            id[i] = Long.valueOf(rolesId[i]);
        }
        return id;
    }
}
