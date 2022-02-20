package com.trafficLight.soo.service;

import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.repository.PostRepository;
import com.trafficLight.soo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> findAll(){
        List<Post> postList = postRepository.findAll();
        return postList;
    }

    public void regist(Post post){

        postRepository.save(post);
    }
}
