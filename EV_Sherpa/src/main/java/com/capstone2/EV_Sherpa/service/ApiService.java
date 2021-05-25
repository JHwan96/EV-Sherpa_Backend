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
    private final Api2Repository api2Repository;

    @Transactional
    public String xmlToDb() throws Exception{

        HtmlUtil htmlUtil = new HtmlUtil();
        int itemSize;
        String test = new String();
        List<ApiInformation> apiInformationList;

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "WJo08u7NjS7h%2FNNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k%2BKrxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("WJo08u7NjS7h/NNZuvLvDZssjBLtqhGdpO939Mzlh9TERxC9Q7k+Krxh0MJfafGGlS8NrJLhDFxcy6kVcp5upA==", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
        urlBuilder.append("&" + URLEncoder.encode("zcode","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/

        apiInformationList = new ArrayList<>();
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
            itemSize = item.size();

            for(int i=0; i < itemSize; i++){        //TODO: 배열리스트에 받아서 saveAll로 저장해보기
                ApiInformation apiInformation = new ApiInformation();
                jObj=(JSONObject)item.get(i);
                System.out.println(jObj.toString());
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

                apiInformationList.add(apiInformation);
            }
           api2Repository.saveAll(apiInformationList);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return test;
    }
}
