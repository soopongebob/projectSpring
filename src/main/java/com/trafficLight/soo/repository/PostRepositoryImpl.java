package com.trafficLight.soo.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trafficLight.soo.controller.PostListForm;
import com.trafficLight.soo.controller.QPostListForm;
import com.trafficLight.soo.controller.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.trafficLight.soo.entity.QPost.post;
import static org.springframework.util.StringUtils.*;

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
        System.out.println("params---------------------------------");
        System.out.println("Content : " + searchCondition.getContent());
        System.out.println("subject : " + searchCondition.getSubject());
        System.out.println("username : " + searchCondition.getUsername());
        //        BooleanBuilder builder = new BooleanBuilder();
//        if (hasText(searchCondition.getContent())) {
//                builder.and(post.content.eq(searchCondition.getContent()));
//        }
//        if (hasText(searchCondition.getSubject())) {
//                builder.and(post.subject.eq(searchCondition.getSubject()));
//        }
//        if (hasText(searchCondition.getUsername())) {
//            builder.and(post.subject.eq(searchCondition.getUsername()));
//        }
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
//                        builder
                        //and 조건으로 검색이 됨
                        subjectEq(searchCondition.getSubject())
                                .or(contentEq(searchCondition.getContent()))
                                .or(usernameEq(searchCondition.getUsername()))
//                        searchConditionEq(searchCondition.getUsername(), searchCondition.getContent(), searchCondition.getSubject())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostListForm> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    //Predicate, BooleanExpression(재조합이 가능), BooleanBuilder(null체크 응용하여 조건 chain 가능)
    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? post.user.username.contains(username) : null;
    }

    private BooleanExpression contentEq(String content) {
        return hasText(content) ? post.content.contains(content) : null;
    }

    private BooleanExpression subjectEq(String subject) {
        return hasText(subject) ? post.subject.contains(subject) : null;
    }
//    private BooleanBuilder searchConditionEq(String username, String content, String subject){
//        return usernameEq(username).or(subjectEq(subject)).or(contentEq(content));
//    }
//    private BooleanBuilder usernameEq(String username) {
//        if(username == null){
//            return new BooleanBuilder();
//        }else{
//            return new BooleanBuilder(post.user.username.eq(username));
//        }
//    }
//    private BooleanBuilder subjectEq(String subject) {
//        if(subject == null){
//            return new BooleanBuilder();
//        }else{
//            return new BooleanBuilder(post.subject.eq(subject));
//        }
//    }
//    private BooleanBuilder contentEq(String content) {
//        if(content == null){
//            return new BooleanBuilder();
//        }else{
//            return new BooleanBuilder(post.content.eq(content));
//        }
//    }


//    private BooleanBuilder subjectEq(String subject) {
//        return hasText(subject) ?  new BooleanBuilder() : new BooleanBuilder(post.subject.eq(subject));
//    }
//    private BooleanBuilder contentEq(String content) {
//        return hasText(content) ?  new BooleanBuilder() : new BooleanBuilder(post.content.eq(content));
//    }

}
