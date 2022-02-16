package com.trafficLight.soo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_idx")
    private Long index;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    private Post post;

    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    //연관관계 편의 메서드 user
    public void setUser(User user){
        this.user = user;
        user.getComments().add(this);
    }

    //연관관계 편의 메서드 post
    public void setPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }
}
