package com.example.schoolmanagementsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.schoolmanagementsystem.entity.Permission.*;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;


@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_CREATE,
            ADMIN_UPDATE,
            ADMIN_DELETE,

            HEADTEACHER_CREATE,
            HEADTEACHER_READ,
            HEADTEACHER_UPDATE,
            HEADTEACHER_DELETE,

            TEACHER_CREATE,
            TEACHER_READ,
            TEACHER_UPDATE,
            TEACHER_DELETE,

            STUDENT_READ,
            STUDENT_UPDATE,
            STUDENT_DELETE,
            STUDENT_CREATE
    )),
    HEADTEACHER(Set.of(
            HEADTEACHER_CREATE,
            HEADTEACHER_READ,
            HEADTEACHER_UPDATE,
            HEADTEACHER_DELETE,

            TEACHER_CREATE,
            TEACHER_READ,
            TEACHER_UPDATE,
            TEACHER_DELETE,

            STUDENT_READ,
            STUDENT_UPDATE,
            STUDENT_DELETE,
            STUDENT_CREATE
    )),
    TEACHER(Set.of(
            TEACHER_CREATE,
            TEACHER_READ,
            TEACHER_UPDATE,
            TEACHER_DELETE,

            STUDENT_READ,
            STUDENT_UPDATE,
            STUDENT_DELETE,
            STUDENT_CREATE
    )),
    STUDENT(Set.of(
            STUDENT_READ,
            STUDENT_UPDATE,
            STUDENT_DELETE,
            STUDENT_CREATE
    ));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities =getPermissions()
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
