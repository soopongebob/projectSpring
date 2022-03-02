package com.trafficLight.soo.repository;

import com.trafficLight.soo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
