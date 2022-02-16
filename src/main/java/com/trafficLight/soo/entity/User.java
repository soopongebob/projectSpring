package com.trafficLight.soo.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String uuid;

    @Column(name = "user_id")
    private String userId;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Auth auth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime joinDate;

    @Builder
    public User(String uuid, String userId, String username, String password, String email, Auth auth) {
        this.uuid = uuid;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.auth = auth;
    }

}
