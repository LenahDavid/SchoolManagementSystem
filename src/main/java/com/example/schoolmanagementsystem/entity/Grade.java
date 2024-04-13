package com.example.schoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@AllArgsConstructor
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;


    @ManyToMany(mappedBy = "classes")
    private Set<Teacher> teachers = new HashSet<>();
}
