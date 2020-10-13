package com.java.zolo.instagram.repository;

import com.java.zolo.instagram.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}
