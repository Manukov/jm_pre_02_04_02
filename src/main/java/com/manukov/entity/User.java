package com.manukov.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    transient private String confirmPassword;

    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long id, String username, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }


    public User(long id, String username, String password, String confirmPassword, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
    }

    //----------------------реализация UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    //Указывает, истек ли срок действия учетной записи пользователя
    @Override
    public boolean isAccountNonExpired() { return true; }            //true - учетная запись пользователя действительна

    //Указывает, заблокирован пользователь или разблокирован.
    @Override
    public boolean isAccountNonLocked() {
        return true;    //пользователь не заблокирован
    }

    //Указывает, истек ли срок действия учетных данных (пароля) пользователя. Просроченные учетные данные препятствуют аутентификации.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;        //учетные данные пользователя действительны
    }

    //Указывает, включен или отключен пользователь. Отключенный пользователь не может быть аутентифицирован.
    @Override
    public boolean isEnabled() {
        return true;            //пользователь включен
    }



    //----------------------getter/setter

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }




}
