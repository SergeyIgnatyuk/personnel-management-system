package com.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enum for {@link com.model.User} role with permissions.
 *
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public enum Role {
    ADMIN(Stream.of(Permission.DEPARTMENTS_WRITE, Permission.DEPARTMENTS_READ,
            Permission.EMPLOYEES_READ, Permission.EMPLOYEES_WRITE).collect(Collectors.toSet())),
    USER(Stream.of(Permission.DEPARTMENTS_READ, Permission.EMPLOYEES_READ).collect(Collectors.toSet()));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
