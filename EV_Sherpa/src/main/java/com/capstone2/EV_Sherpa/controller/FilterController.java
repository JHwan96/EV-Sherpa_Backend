package com.capstone2.EV_Sherpa.controller;

import com.capstone2.EV_Sherpa.domain.Preference;
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
        String result = filterService.prefFromBusinessName(request.getEmail());
        return new businessNameResponse(result);
    }

    @PostMapping("/filter/fastCharge")
    public fastChargeResponse prefFastCharge(@Valid fastChargeRequest request){
        String result = filterService.prefFromFastCharge(request.getEmail());
        return new fastChargeResponse(result);
    }

    @PostMapping("/filter/ChargerType")
    public ChargerTypeResponse prefChargerType(@Valid ChargerTypeRequest request){
        String result = filterService.prefFromChargerType(request.getEmail());
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
    }

}
