package com.trafficLight.soo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_idx")
    private Long postIdx;
    private Categories category;    //enum [NOTICE, ETC, QNA, FAQ]
    private String subject;
    private String content;

    @Column(name = "view_count")
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    //연관관계 편의 메서드
    public void setUser(User user){
        this.user = user;
        user.getPosts().add(this);
    }
}
