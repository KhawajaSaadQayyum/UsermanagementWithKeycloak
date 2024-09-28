package com.codeWithSaad.repository;

import com.codeWithSaad.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
