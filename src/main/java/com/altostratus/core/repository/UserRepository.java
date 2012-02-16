package com.altostratus.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.core.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
