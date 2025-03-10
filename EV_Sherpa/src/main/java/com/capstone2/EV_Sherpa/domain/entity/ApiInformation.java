package com.capstone2.EV_Sherpa.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "api_information")
public class ApiInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "statId")
    private String statId;            //충전소 ID

    @Column(name = "statNm")
    private String statNm; //충전소명

    @Column(name = "chgerId")
    private String chgerId;     //충전기 ID

    @Column(name = "chgerType")
    private String chgerType;     //충전기 타입

    @Column(name = "addr")
    private String addr;        //주소

    @Column(name = "lat")
    private Float lat;           //위도

    @Column(name = "lng")
    private Float lng;           //경도

    @Column(name = "useTime")
    private String useTime;           //이용가능시간

    @Column(name = "busiId")
    private String busiId;           //기관 아이디

    @Column(name = "busiNm")
    private String busiNm;           //운영기관명

    @Column(name = "busiCall")
    private String busiCall;           //연락처

    @Column(name = "stat")
    private Long stat;           //충전기 상태

    @Column(name = "statUpdDt")
    private String statUpdDt;           //상태 갱신 일시

    @Column(name = "powerType")
    private String powerType;           //충전량

    @Column(name = "zcode")
    private String zcode;           //지역코드

    @Column(name = "parkingFree")
    private String parkingFree;           //주차료무료

    @Column(name = "note")
    private String note;           //충전소 안내

    @Column(name = "limitYn")
    private String limitYn;

    @Column(name = "limitDetail")
    private String limitDetail;

    @Column(name = "delYn")
    private String delYn;

    @Column(name = "delDetail")
    private String delDetail;
}
