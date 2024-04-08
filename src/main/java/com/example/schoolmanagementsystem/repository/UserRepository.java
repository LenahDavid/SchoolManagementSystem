package com.example.schoolmanagementsystem.repository;


import com.example.schoolmanagementsystem.entity.Role;
import com.example.schoolmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    User findByRole(Role role);
User findByEmail(String email);
User findByUsername(String username);
}

