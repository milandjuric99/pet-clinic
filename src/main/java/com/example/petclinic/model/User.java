package com.example.petclinic.model;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private boolean enabled;

    @ManyToMany(cascade = {
        CascadeType.MERGE,
        CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(Integer id, String username, String password, boolean enabled, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }
}
