package com.capstone2.EV_Sherpa.controller;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
public class ApiController {
    @GetMapping("/api")
    public String callapihttp() throws IOException {

        StringBuffer result = new StringBuffer();
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerStatus"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=WJo08u7NjS7h%2FNNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k%2BKrxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode("WJo08u7NjS7h/NNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k+Krxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
            urlBuilder.append("&" + URLEncoder.encode("period", "UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*상태갱신 조회 범위(분) (기본값 5, 최소 1, 최대 10)*/
            urlBuilder.append("&" + URLEncoder.encode("zcode", "UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String returnLine;
//            result.append("<xmp>");
            while((returnLine = br.readLine()) != null){
                result.append(returnLine + "\n\r");
            }
            conn.disconnect();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();//result+"</xmp>";
    }

    public void init(String jsonData){
        try {
            JSONObject jObj;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonData);

            JSONObject parseResponse = (JSONObject)jsonObj.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject array = (JSONObject)parseBody.get("items");

            for(int i = 0; i < array.size();i++){
                jObj=(JSONObject)array.get(i);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
