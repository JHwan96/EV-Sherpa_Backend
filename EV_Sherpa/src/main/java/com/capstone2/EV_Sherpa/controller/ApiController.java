package com.capstone2.EV_Sherpa.controller;

import com.capstone2.EV_Sherpa.service.ApiService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ApiService apiService;

    @PostMapping("/api")
    @ResponseBody
    public apiInfoResponse callApi(apiInfoRequest request) throws Exception {
        String result;
        result = apiService.xmlToDb(request.getLatitude(), request.getLongitude());
        String changeResult = new String(result.getBytes("8859_1"),"utf-8");
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
    static class apiInfoRequest{
        private Float latitude;
        private Float longitude;
    }

    @Data
    @AllArgsConstructor
    static class statusApiResponse {
        private String statusList;
    }
}