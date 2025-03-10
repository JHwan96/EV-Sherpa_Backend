package com.capstone2.EV_Sherpa.controller;

import com.capstone2.EV_Sherpa.service.FilterService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class FilterController {
    private final FilterService filterService;

    @PostMapping("/filter/businessName")
    public businessNameResponse prefBusinessName(@Valid businessNameRequest request) {
        String result = filterService.prefFromBusinessName(request.getEmail(), request.getLatitude(), request.getLongitude());
        return new businessNameResponse(result);
    }

    @PostMapping("/filter/fastCharge")
    public fastChargeResponse prefFastCharge(@Valid fastChargeRequest request){
        String result = filterService.prefFromFastCharge(request.getEmail(), request.getLatitude(), request.getLongitude());
        return new fastChargeResponse(result);
    }

    @PostMapping("/filter/chargerType")
    public ChargerTypeResponse prefChargerType(@Valid ChargerTypeRequest request){
        String result = filterService.prefFromChargerType(request.getEmail(), request.getLatitude(), request.getLongitude());
        return new ChargerTypeResponse(result);
    }

    @PostMapping("/filter/distance")
    public ChargerTypeResponse prefDistance(@Valid ChargerTypeRequest request){
        String result = filterService.prefFromDistance(request.getEmail(), request.getLatitude(), request.getLongitude());
        return new ChargerTypeResponse(result);
    }

    @Data
    @AllArgsConstructor
    static class businessNameResponse{
        private String statId;
    }

    @Data
    @AllArgsConstructor
    static class businessNameRequest{
        private String email;
        private Float latitude;
        private Float longitude;
    }

    @Data
    @AllArgsConstructor
    static class fastChargeResponse{
        private String statId;
    }

    @Data
    @AllArgsConstructor
    static class fastChargeRequest{
        private String email;
        private Float latitude;
        private Float longitude;
    }

    @Data
    @AllArgsConstructor
    static class ChargerTypeResponse{
        private String statId;
    }

    @Data
    @AllArgsConstructor
    static class ChargerTypeRequest{
        private String email;
        private Float latitude;
        private Float longitude;
    }

    @Data
    @AllArgsConstructor
    static class DistanceResponse{
        private String statId;
    }

    @Data
    @AllArgsConstructor
    static class DistanceRequest{
        private String email;
        private Float latitude;
        private Float longitude;
    }

}
