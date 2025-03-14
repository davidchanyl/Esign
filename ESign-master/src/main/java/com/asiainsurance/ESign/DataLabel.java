package com.asiainsurance.ESign;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataLabel {

    private String fieldName;
    private String content;
    private String title;
    private List<String> contentList;
    private List<String> extraField;
    private String textType;
    private List<CheckBoxLabel> checkList;
    private List<String> valueList;
    private List<String> extraList;

    public DataLabel(JSONObject json){

        fieldName = null;
        content = null;
        title = null;
        contentList = null;
        extraField = new ArrayList<>();
        textType = null;
        checkList = null;
        getData(json);

    }

    public String getFieldName() {
        return fieldName;
    }

    public String getContent(){
        return content;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContentList() {
        return contentList;
    }

    public List<String> getExtraField() {
        return extraField;
    }

    public String getTextType() {
        return textType;
    }

    public List<CheckBoxLabel> getCheckList() {
        return checkList;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public List<String> getExtraList() {
        return extraList;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

    public void setFieldName(String fieldname) {
        this.fieldName = fieldname;
    }

    public void setExtraField(List<String> extraField) {
        this.extraField = extraField;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCheckList(List<CheckBoxLabel> checkList) {
        this.checkList = checkList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public void setExtraList(List<String> extraList) {
        this.extraList = extraList;
    }

    public void getData(JSONObject item){

        Set<String> keys = item.keySet();
        textType = keys.iterator().next();


        if (textType.matches("(?i)title|subtitle|text|subtext")){
            content = item.getString(textType);
        } else {
            JSONObject itemContent = item.getJSONObject(textType);
            keys = itemContent.keySet();
            for(String key : keys){

                switch (key){
                    case "fieldName":
                        setFieldName(itemContent.getString(key));
                        break;

                    case "content":
                        setContent(itemContent.getString(key));
                        break;

                    case "title":
                        setTitle(itemContent.getString(key));

                        if (title.equals("Null")){
                            setTitle(null);
                        }
                        break;

                    case "contentList":
                        setContentList(arrayToList(itemContent.getJSONArray(key)));
                        break;

                    case "extraField":
                        setExtraField(arrayToList(itemContent.getJSONArray(key)));
                        break;
                }
            }

            if (contentList != null){
                if (extraField.isEmpty()){
                    for(Object obj: contentList){

                        extraField.add("False");
                    }
                }

                valueList = new ArrayList<>(contentList);
                checkList = new ArrayList<>(contentList.size());
                extraList = new ArrayList<>(extraField);

                for (int i = 0; i < contentList.size(); i++){

                    CheckBoxLabel checkLabel = new CheckBoxLabel(contentList.get(i), extraField.get(i), fieldName, i);

                    checkList.add(checkLabel);

                }
            }
        }
    }

    public List<String> arrayToList(JSONArray json) {

        List<String> newList = new ArrayList<>();

        for (Object obj : json) {
            String str = obj.toString();
            newList.add(str);

        }
        return newList;
    }

}
