package com.trafficLight.soo.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trafficLight.soo.controller.PostListForm;
import com.trafficLight.soo.controller.QPostListForm;
import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.entity.QPost;
import com.trafficLight.soo.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.trafficLight.soo.entity.QPost.post;

public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    public PostRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<PostListForm> pagination(Pageable pageable) {
        QueryResults<PostListForm> results = queryFactory
                .select(new QPostListForm(
                        post.postIdx,
                        post.subject,
                        post.user.username,
                        post.viewCount,
                        post.createdDate
                ))
                .from(post)
                .orderBy(post.postIdx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<PostListForm> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl(content, pageable, total);
    }

}
