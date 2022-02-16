package com.trafficLight.soo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private UUID uuid;

    @Column(name = "user_id")
    private String userId;

    private String username;
    private String password;
    private String email;

    private Auth auth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime joinDate;

    public User(UUID uuid, String userId, String username, String password, String email, Auth auth) {
        this.uuid = uuid;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.auth = auth;
    }

}
