package com.capstone2.EV_Sherpa.utils;

import org.json.XML;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HtmlUtil {
    public String HtmlParser(String urlToRead){
        StringBuffer result = new StringBuffer();
        String xmlToJSON = new String();

        try {
            URL url = new URL(urlToRead);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

            String returnLine;

            while((returnLine = br.readLine()) !=null){
                result.append(returnLine);
            }
            urlConnection.disconnect();

/*
            InputStream is = url.openStream();
            int ch;

            while((ch = is.read()) != -1){
                result.append((char) ch);
            }*/
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        org.json.JSONObject xmlJSONObj = XML.toJSONObject(result.toString());
        xmlToJSON = xmlJSONObj.toString(4);
        return xmlToJSON;
    }
}
