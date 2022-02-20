package com.trafficLight.soo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Manager {

    @Id
    @Column(name = "manager_id")
    private String managerId;

    private String password;

    private String auth;

    public Manager(String managerId, String password, String auth) {
        this.managerId = managerId;
        this.password = password;
        this.auth = auth;
    }
}
