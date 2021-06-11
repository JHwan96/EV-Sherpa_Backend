package com.capstone2.EV_Sherpa.controller;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.service.ApiService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/api")
    public apiInfoResponse callApi() throws Exception {
        String result;

        result = apiService.xmlToDb();
        return new apiInfoResponse(result);
    }

    @GetMapping("/statusApi")
    public statusApiResponse callApiStatus() throws Exception {
        String result;
        result = apiService.statusXmlToString();
        return new statusApiResponse(result);
    }

    @Data
    @AllArgsConstructor
    static class apiInfoResponse{
        private String apiInfo;
    }

    @Data
    @AllArgsConstructor
    static class statusApiResponse {
        private String statusList;
    }

}