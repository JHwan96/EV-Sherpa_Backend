package com.capstone2.EV_Sherpa.service;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApiService {
    private final ApiRepository apiRepository;

    @Transactional
    public void xmlToDb(String jsonData){
        ApiInformation apiInformation = new ApiInformation();
        try {
            JSONObject jObj;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonData);

            JSONObject parseResponse = (JSONObject)jsonObj.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject array = (JSONObject)parseBody.get("items");

            for(int i = 0; i < array.size();i++){
                jObj=(JSONObject)array.get(i);
                apiInformation.setStatId(jObj.get("statId").toString());
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
