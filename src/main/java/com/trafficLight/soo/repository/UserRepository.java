package com.trafficLight.soo.repository;

import com.trafficLight.soo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);
}