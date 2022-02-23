package com.trafficLight.soo.repository;

import com.trafficLight.soo.controller.PostListForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostListForm> pagination(Pageable pageable);
}
