package com.capstone2.EV_Sherpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "reviews")

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "stationId")
    private Long stationId;

    @Column(name = "carName")
    private String carName;

    @Column(name = "batteryType")
    private String batteryType;

}
