package com.capstone2.EV_Sherpa.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "car_name")
    private String carName;

    @Column(name = "age")
    private Long age;

    @Column(name = "homeAddr")
    private String homeAddr;

    @Column(name = "workplaceAddr")
    private String workplaceAddr;
}
