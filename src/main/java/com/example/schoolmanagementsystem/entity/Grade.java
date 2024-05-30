package com.example.schoolmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
//@AllArgsConstructor
//@AllArgsConstructor
@Table(name = "classes")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "class_id")
   private Long id;
    @Column(name = "class_name")
    private String name;

//
//    @ManyToMany(mappedBy = "classes")
//    private Set<Teacher> teachers = new HashSet<>();
}
