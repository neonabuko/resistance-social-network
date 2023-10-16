package com.api.resistancesocialnetwork.entity;

import com.api.resistancesocialnetwork.enums.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "user")
@Table(name = "users")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL)
    private Rebel rebel;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    @OneToOne(cascade = CascadeType.ALL)
    private Inventory inventory;

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRebel(Rebel rebel) {
        this.rebel = rebel;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getLogin() {
        return login;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + ", role=" + role + '}';
    }
}


