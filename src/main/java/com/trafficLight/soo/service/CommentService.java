package com.trafficLight.soo.service;

import com.trafficLight.soo.entity.Comment;
import com.trafficLight.soo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void save(Comment comment){
        commentRepository.save(comment);
    }

}
