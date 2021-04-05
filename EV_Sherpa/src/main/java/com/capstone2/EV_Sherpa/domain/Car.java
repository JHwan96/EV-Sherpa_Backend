package com.capstone2.EV_Sherpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "chargerType")
    private String chargerType;

    @Column(name = "batteryType")
    private String batteryType;
}
