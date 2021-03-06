package com.trafficLight.soo.repository;

import com.trafficLight.soo.controller.PostViewForm;
import com.trafficLight.soo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Post findByPostIdx(Long postIdx);
}
