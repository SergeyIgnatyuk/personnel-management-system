package com.model;

/**
 * Enum for {@link com.model.Role} permissions.
 *
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public enum Permission {
    DEPARTMENTS_READ("departments:read"),
    DEPARTMENTS_WRITE("departments:write"),
    EMPLOYEES_READ("employees:read"),
    EMPLOYEES_WRITE("employees:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
