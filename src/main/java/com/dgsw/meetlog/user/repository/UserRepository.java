package com.dgsw.meetlog.user.repository;

import com.dgsw.meetlog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
