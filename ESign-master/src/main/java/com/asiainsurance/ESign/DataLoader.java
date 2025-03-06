package com.asiainsurance.ESign;
import org.json.JSONArray;
import org.springframework.util.ResourceUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DataLoader {

    public JSONArray getPdfContent(String name) {
       try {
           String dirpath = "classpath:static/";
           String clspath = dirpath.concat(name);
           clspath = clspath.concat(".json");
           File file = ResourceUtils.getFile(clspath);
            //Read File Content
           String content = new String(Files.readAllBytes(file.toPath()));
           JSONObject json = new JSONObject(content);

           return json.getJSONArray("content");
       } catch (IOException e) {
           //noinspection CallToPrintStackTrace
           e.printStackTrace();
        }
            return null;
    }
}
