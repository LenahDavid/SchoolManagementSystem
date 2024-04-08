package com.example.schoolmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
//@Data
public enum Permission {

    USER_READ("student:read"),
    USER_CREATE("student:create"),
    USER_UPDATE("student:update"),
    USER_DELETE("student:delete"),

    TEACHER_READ("teacher:read"),
    TEACHER_CREATE("teacher:create"),
    TEACHER_UPDATE("teacher:update"),
    TEACHER_DELETE("teacher:delete"),

    HEADTEACHER_READ("headteacher:read"),
    HEADTEACHER_CREATE("headteacher:create"),
    HEADTEACHER_UPDATE("headteacher:update"),
    HEADTEACHER_DELETE("headteacher:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:write"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete")
    ;

    private final String permission;
}
