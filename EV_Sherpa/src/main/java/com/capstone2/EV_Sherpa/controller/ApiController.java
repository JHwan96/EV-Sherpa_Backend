package com.capstone2.EV_Sherpa.controller;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.service.ApiService;
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
    public String callApi() throws Exception {
        String result;

        result = apiService.xmlToDb();   // new String("hello");
        return result;//result+"</xmp>";
    }
}
