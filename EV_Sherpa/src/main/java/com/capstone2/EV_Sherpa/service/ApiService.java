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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9999", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
        urlBuilder.append("&" + URLEncoder.encode("period","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*상태갱신 조회 범위(분) (기본값 5, 최소 1, 최대 10)*/
        urlBuilder.append("&" + URLEncoder.encode("zcode","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/

        String jsonData = new HtmlUtil().HtmlParser(urlBuilder.toString());
        try {

            org.json.JSONObject jOb = new org.json.JSONObject(jsonData);
            org.json.JSONObject parseResponse = jOb.getJSONObject("response");
            org.json.JSONObject parseBody = parseResponse.getJSONObject("body");
            org.json.JSONObject parseItems = parseBody.getJSONObject("items");
            org.json.JSONArray item = parseItems.getJSONArray("item");
            result = jOb.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Transactional
    public String xmlToDb(Float latitude, Float longitude) throws Exception{
        List<ApiInformation> apiInformationList = new ArrayList<>();
        apiInformationList = apiRepository.findNearCharger(latitude, longitude);
        JSONArray jsonArray = new JSONArray();

        List <Map<String,Object>> list_map = new ArrayList<>(); // 리스트 생성
        int length = apiInformationList.size();

        for(int i = 0; i <length; i++) {
            Map<String, Object> map = new HashMap<>(); // 키, 밸류 데이터를 담기위한 맵 인스턴스 생성
            map.put("zcode", apiInformationList.get(i).getZcode()); // 맵에 데이터 삽입
            map.put("statId", apiInformationList.get(i).getStatId()); // 맵에 데이터 삽입
            map.put("stat", apiInformationList.get(i).getStat()); // 맵에 데이터 삽입
            map.put("note", apiInformationList.get(i).getNote()); // 맵에 데이터 삽입
            map.put("lng", apiInformationList.get(i).getLng()); // 맵에 데이터 삽입
            map.put("busiId", apiInformationList.get(i).getBusiId()); // 맵에 데이터 삽입
            map.put("busiCall", apiInformationList.get(i).getBusiCall()); // 맵에 데이터 삽입
            map.put("busiNm", apiInformationList.get(i).getBusiNm()); // 맵에 데이터 삽입
            map.put("parkingFree", apiInformationList.get(i).getParkingFree()); // 맵에 데이터 삽입
            map.put("limitYn", apiInformationList.get(i).getLimitYn()); // 맵에 데이터 삽입
            map.put("delYn", apiInformationList.get(i).getDelYn()); // 맵에 데이터 삽입
            map.put("chgerId", apiInformationList.get(i).getChgerId()); // 맵에 데이터 삽입
            map.put("chgerType", apiInformationList.get(i).getChgerType()); // 맵에 데이터 삽입
            map.put("statUpdDt", apiInformationList.get(i).getStatUpdDt()); // 맵에 데이터 삽입
            map.put("limitDetail", apiInformationList.get(i).getLimitDetail()); // 맵에 데이터 삽입
            map.put("useTime", apiInformationList.get(i).getUseTime()); // 맵에 데이터 삽입
            map.put("addr", apiInformationList.get(i).getAddr()); // 맵에 데이터 삽입
            map.put("statNm", apiInformationList.get(i).getStatNm()); // 맵에 데이터 삽입
            map.put("lat", apiInformationList.get(i).getLat()); // 맵에 데이터 삽입
            map.put("delDetail", apiInformationList.get(i).getDelDetail()); // 맵에 데이터 삽입
            map.put("powerType", apiInformationList.get(i).getPowerType()); // 맵에 데이터 삽입
            list_map.add(map);
        }

        int listLength = list_map.size();
        for(int i = 0; i < listLength; i++){
            jsonArray.add(list_map.get(i));
        }
        String result = jsonArray.toJSONString();


        return result;
    }
}

