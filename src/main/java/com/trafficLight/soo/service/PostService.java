package com.trafficLight.soo.service;

import com.trafficLight.soo.controller.PostListForm;
import com.trafficLight.soo.controller.PostViewForm;
import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<PostListForm> findAll(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);
        Page<PostListForm> posts = postRepository.pagination(pageable);
        return posts;
    }

    public void registration(Post post){
        postRepository.save(post);
    }

    public Post findByPostIdx(Long postIdx){
        Post post = postRepository.findByPostIdx(postIdx);
        return post;
    }
}
