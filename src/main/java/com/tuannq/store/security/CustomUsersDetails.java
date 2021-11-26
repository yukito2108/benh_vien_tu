package com.tuannq.store.security;

import com.tuannq.store.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomUsersDetails implements UserDetails {

    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String email;
    private Long id;
    private String firstName;
    private String lastName;

    public CustomUsersDetails(Long id, String firstName, String lastName, String userName, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUsersDetails create(Users users) {
        List<GrantedAuthority> authorities =
                users.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return new CustomUsersDetails(
                users.getId(),
                users.getFirstName(),
                users.getLastName(),
                users.getUserName(),
                users.getEmail(),
                users.getPassword(),
                authorities
        );
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean hasRole(String roleName) {
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CustomUsersDetails that = (CustomUsersDetails) object;
        return Objects.equals(id, that.id);
    }
}
