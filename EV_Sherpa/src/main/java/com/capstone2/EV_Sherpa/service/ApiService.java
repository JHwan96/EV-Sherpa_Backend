package com.capstone2.EV_Sherpa.service;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.repository.ApiRepository;
import com.capstone2.EV_Sherpa.utils.HtmlUtil;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApiService {
    private final ApiRepository apiRepository;

    @Transactional
    public String xmlToDb() throws Exception{
        ApiInformation apiInformation = new ApiInformation();
        HtmlUtil htmlUtil = new HtmlUtil();

        String test = new String();
//            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerStatus"); /*URL*/
//            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=WJo08u7NjS7h%2FNNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k%2BKrxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA%3D%3D"); /*Service Key*/
//            urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode("WJo08u7NjS7h/NNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k+Krxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
//            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
//            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
//            urlBuilder.append("&" + URLEncoder.encode("period", "UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*상태갱신 조회 범위(분) (기본값 5, 최소 1, 최대 10)*/
//            urlBuilder.append("&" + URLEncoder.encode("zcode", "UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "WJo08u7NjS7h%2FNNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k%2BKrxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("WJo08u7NjS7h/NNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k+Krxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
        urlBuilder.append("&" + URLEncoder.encode("zcode","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/


        String jsonData = new HtmlUtil().HtmlParser(urlBuilder.toString());
       try {
            JSONObject jObj;

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);

            JSONObject parseResponse = (JSONObject)jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject parseItems = (JSONObject)parseBody.get("items");
            JSONArray item = (JSONArray) parseItems.get("item");
            test = parseItems.toString();

            for(int i = 0; i < item.size();i++){
                jObj=(JSONObject)item.get(i);
                apiInformation.setStatId(jObj.get("statId").toString());
                apiInformation.setStatNm(jObj.get("statNm").toString());
                apiInformation.setChgerId(jObj.get("chgerId").toString());
                apiInformation.setChgerType(jObj.get("chgerType").toString());
                apiInformation.setAddr(jObj.get("addr").toString());
                apiInformation.setLat(Float.valueOf(jObj.get("lat").toString()));
                apiInformation.setLng(Float.valueOf(jObj.get("lng").toString()));
                apiInformation.setUseTime(jObj.get("useTime").toString());
                apiInformation.setBusiId(jObj.get("busiId").toString());
                apiInformation.setBusiNm(jObj.get("busiNm").toString());
                apiInformation.setBusiCall(jObj.get("busiCall").toString());
                apiInformation.setStat(Long.valueOf(jObj.get("stat").toString()));
                apiInformation.setStatUpdDt(jObj.get("statUpdDt").toString());
                apiInformation.setPowerType(jObj.get("powerType").toString());
                apiInformation.setZcode(jObj.get("zcode").toString());
                apiInformation.setParkingFree(jObj.get("parkingFree").toString());
                apiInformation.setNote(jObj.get("note").toString());
                apiInformation.setLimitYn(jObj.get("limitYn").toString());
                apiInformation.setLimitDetail(jObj.get("limitDetail").toString());
                apiInformation.setDelYn(jObj.get("delYn").toString());
                apiInformation.setDelDetail(jObj.get("delDetail").toString());

                apiRepository.save(apiInformation);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return test;
    }
}