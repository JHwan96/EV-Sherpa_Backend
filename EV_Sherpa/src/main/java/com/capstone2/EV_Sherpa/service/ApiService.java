package com.capstone2.EV_Sherpa.service;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.repository.Api2Repository;
import com.capstone2.EV_Sherpa.repository.ApiRepository;
import com.capstone2.EV_Sherpa.utils.HtmlUtil;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApiService {
    private final ApiRepository apiRepository;

    @Transactional
    public String statusXmlToString() throws Exception{
        String result = new String();

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerStatus"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "WJo08u7NjS7h%2FNNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k%2BKrxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("WJo08u7NjS7h/NNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k+Krxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
        urlBuilder.append("&" + URLEncoder.encode("period","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*상태갱신 조회 범위(분) (기본값 5, 최소 1, 최대 10)*/
        urlBuilder.append("&" + URLEncoder.encode("zcode","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/

        String jsonData = new HtmlUtil().HtmlParser(urlBuilder.toString());
        try {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);

            JSONObject parseResponse = (JSONObject)jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject parseItems = (JSONObject)parseBody.get("items");
            JSONArray item = (JSONArray) parseItems.get("item");
            result = item.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public String xmlToDb() throws Exception{

        HtmlUtil htmlUtil = new HtmlUtil();
        int itemSize;
        String result = "[";
        String tempResult = "";
        String jsonData = new String();
        List<ApiInformation> apiInformationList = new ArrayList<>();
        org.json.JSONObject jOb;
        org.json.JSONArray item = new org.json.JSONArray();

                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "WJo08u7NjS7h%2FNNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k%2BKrxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA%3D%3D"); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode("WJo08u7NjS7h/NNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k+Krxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("9999", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
                urlBuilder.append("&" + URLEncoder.encode("zcode", "UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/

                jsonData = new HtmlUtil().HtmlParser(urlBuilder.toString());

                try {
///?????
                    jOb = new org.json.JSONObject(jsonData);
                    org.json.JSONObject parseResponse = jOb.getJSONObject("response");
                    org.json.JSONObject parseBody = parseResponse.getJSONObject("body");
                    org.json.JSONObject parseItems = parseBody.getJSONObject("items");
                    item = parseItems.getJSONArray("item");
                    result = item.toString().replace("]", "");
                    result = result.replace("[","");
                } catch (Exception e) {
                    e.printStackTrace();
                }




        return result;
    }
}
