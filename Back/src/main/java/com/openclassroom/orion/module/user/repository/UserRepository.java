package com.openclassroom.orion.module.user.repository;

import com.openclassroom.orion.module.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
