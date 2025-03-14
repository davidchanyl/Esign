package com.asiainsurance.ESign;

public class CheckBoxLabel {
    private String fieldName;
    private String content;
    private String extraField;

    public CheckBoxLabel(String c, String ef, String fN, int i){

        content = c;
        extraField = ef;
        fieldName = fN + i;
    }

    public String getContent() {
        return content;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
