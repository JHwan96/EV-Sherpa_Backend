package com.capstone2.EV_Sherpa.utils;

import com.capstone2.EV_Sherpa.domain.ApiInformation;
import com.capstone2.EV_Sherpa.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner{
    private final ApiRepository apiRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        HtmlUtil htmlUtil = new HtmlUtil();
        int itemSize;
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

            jOb = new org.json.JSONObject(jsonData);
            org.json.JSONObject parseResponse = jOb.getJSONObject("response");
            org.json.JSONObject parseBody = parseResponse.getJSONObject("body");
            org.json.JSONObject parseItems = parseBody.getJSONObject("items");
            item = parseItems.getJSONArray("item");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        itemSize = item.length();
        for(int i=0; i < itemSize; i++){        //TODO: 배열리스트에 받아서 saveAll로 저장해보기
            ApiInformation apiInformation;
            jOb=item.getJSONObject(i);

            List<ApiInformation> apiInformation2 = apiRepository.findByStatId(jOb.get("statId").toString());
            if(!apiInformation2.isEmpty()){
                apiInformation = apiInformation2.get(0);
            }
            else {
                apiInformation = new ApiInformation();
            }
            apiInformation.setStatId(jOb.get("statId").toString());
            apiInformation.setStatNm(jOb.get("statNm").toString());
            apiInformation.setChgerId(jOb.get("chgerId").toString());
            apiInformation.setChgerType(jOb.get("chgerType").toString());
            apiInformation.setAddr(jOb.get("addr").toString());
            apiInformation.setLat(Float.valueOf(jOb.get("lat").toString()));
            apiInformation.setLng(Float.valueOf(jOb.get("lng").toString()));
            apiInformation.setUseTime(jOb.get("useTime").toString());
            apiInformation.setBusiId(jOb.get("busiId").toString());
            apiInformation.setBusiNm(jOb.get("busiNm").toString());
            apiInformation.setBusiCall(jOb.get("busiCall").toString());
            apiInformation.setStat(Long.valueOf(jOb.get("stat").toString()));
            apiInformation.setStatUpdDt(jOb.get("statUpdDt").toString());
            apiInformation.setPowerType(jOb.get("powerType").toString());
            apiInformation.setZcode(jOb.get("zcode").toString());
            apiInformation.setParkingFree(jOb.get("parkingFree").toString());
            apiInformation.setNote(jOb.get("note").toString());
            apiInformation.setLimitYn(jOb.get("limitYn").toString());
            apiInformation.setLimitDetail(jOb.get("limitDetail").toString());
            apiInformation.setDelYn(jOb.get("delYn").toString());
            apiInformation.setDelDetail(jOb.get("delDetail").toString());
            apiRepository.save(apiInformation);

        }

    }
}
