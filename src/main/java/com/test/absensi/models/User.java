package com.test.absensi.models;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private String email;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "perusahaan", nullable = false)
    private Perusahaan perusahaan;

    @Getter
    @Enumerated(EnumType.STRING)
    private Profile profile;

    public User() {}

    public User(String email, String password, Profile profile, Perusahaan perusahaan) {
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.perusahaan = perusahaan;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(profile.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
