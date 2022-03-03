package com.trafficLight.soo.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trafficLight.soo.controller.PostListForm;
import com.trafficLight.soo.controller.QPostListForm;
import com.trafficLight.soo.controller.SearchCondition;
import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.entity.QPost;
import com.trafficLight.soo.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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

    @Override
    public Page<PostListForm> searchPost(SearchCondition searchCondition, Pageable pageable) {
        QueryResults<PostListForm> results = queryFactory
                .select(new QPostListForm(
                        post.postIdx,
                        post.subject,
                        post.user.username,
                        post.viewCount,
                        post.createdDate
                ))
                .from(post)
                .where(
                        subjectEq(searchCondition.getSubject()),
                        contentEq(searchCondition.getContent()),
                        usernameEq(searchCondition.getUsername())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostListForm> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression usernameEq(String username) {
        return StringUtils.hasText(username) ? post.user.username.eq(username) : null;
    }

    private BooleanExpression contentEq(String content) {
        return StringUtils.hasText(content) ? post.content.eq(content) : null;
    }

    private BooleanExpression subjectEq(String subject) {
        return StringUtils.hasText(subject) ? post.subject.eq(subject) : null;
    }

}
