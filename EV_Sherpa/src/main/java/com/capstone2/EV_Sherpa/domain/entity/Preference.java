package com.capstone2.EV_Sherpa.domain.entity;

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

    @Column(name = "chargerType")       //ok
    private String chargerType;

    @Column(name = "fastCharge")
    private Boolean fastCharge;

    @Column(name = "remainingCharger")
    private Long remainingCharger;

    @Column(name = "businessName")      //ok
    private String businessName;

}
