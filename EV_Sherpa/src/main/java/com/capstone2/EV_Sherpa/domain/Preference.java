package com.capstone2.EV_Sherpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "preferences")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private Long id;

    @Column(name = "distance")
    private Long distance;

    @Column(name = "chargerType")
    private Long chargerType;

    @Column(name = "batteryType")
    private String batteryType;

    @Column(name = "fastCharge")
    private Boolean fastCharge;

    @Column(name = "remainingCharger")
    private Long remainingCharger;

    @Column(name = "businessName")
    private String businessName;

}
