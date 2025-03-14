package com.asiainsurance.ESign;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties.UiService.LOGGER;

public class DataList {

    private List<DataLabel> dataList;

    public DataList(String name){

        JSONArray json = getPdfContent(name);

        int size = json.length();
        dataList = new ArrayList<>();

        for (int i = 0; i< size; i++){
            JSONObject item = json.getJSONObject(i);
            DataLabel dataLabel = new DataLabel(item);
            dataList.add(dataLabel);
        }

    }

    public DataList(){

        JSONArray json = getPdfContent("sample");

        int size = json.length();
        dataList = new ArrayList<>();

        for (int i = 0; i< size; i++){
            JSONObject item = json.getJSONObject(i);
            DataLabel dataLabel = new DataLabel(item);
            dataList.add(dataLabel);
        }

    }

    public void setDataList(List<DataLabel> dataList) {
        this.dataList = dataList;
    }

    public List<DataLabel> getDataList() {
        return dataList;
    }

    public JSONArray getPdfContent(String name) {
        try {
            String dirpath = "";
            String clspath = dirpath.concat(name);
            clspath = clspath.concat(".json");

            Resource resource = new ClassPathResource(clspath);

            InputStream inputStream = resource.getInputStream();

            String content = null;
            try {
                byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
                content = new String(bdata);
            } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }

            //Read File Content
            JSONObject json = new JSONObject(content);

            return json.getJSONArray("content");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        return null;
    }

    public void initDataList(String name){
        JSONArray json = getPdfContent(name);

        int size = json.length();
        dataList = new ArrayList<>();

        for (int i = 0; i< size; i++){
            JSONObject item = json.getJSONObject(i);
            DataLabel dataLabel = new DataLabel(item);
            dataList.add(dataLabel);
        }
    }

    public void updateData(String body){

    }
}
