package com.trafficLight.soo.repository;

import com.trafficLight.soo.controller.PostListForm;
import com.trafficLight.soo.controller.SearchCondition;
import com.trafficLight.soo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostListForm> pagination(Pageable pageable);
    Page<PostListForm> searchPost(SearchCondition searchCondition, Pageable pageable);
}
