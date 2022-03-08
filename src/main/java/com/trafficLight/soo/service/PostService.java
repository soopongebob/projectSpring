package com.trafficLight.soo.service;

import com.trafficLight.soo.controller.PostEditForm;
import com.trafficLight.soo.controller.PostListForm;
import com.trafficLight.soo.controller.PostViewForm;
import com.trafficLight.soo.controller.SearchCondition;
import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void save(Post post){
        postRepository.save(post);
    }

    @Transactional
    public Post findByPostIdx(Long postIdx){
        Post post = postRepository.findByPostIdx(postIdx);
        //조회수 update
        post.editViewCount(post.getViewCount());
        return post;
    }

    public void delete(Long postIdx){
        postRepository.deleteById(postIdx);
    }

    public Page<PostListForm> search(SearchCondition searchCondition, Pageable pageable){
        Page<PostListForm> postListForms = postRepository.searchPost(searchCondition, pageable);
        return postListForms;
    }
}
