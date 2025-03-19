package com.asiainsurance.ESign;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

public class PdfCreator {

    public static float FONT_SIZE_DEFAULT = 10f;
    public static String REGULAR = "fonts/NotoSansCJKsc-Regular.otf";

    public void createPdf(String name, DataList dataList) throws IOException {

        String dirpath = "";
        String clspath = dirpath.concat(name);
        clspath = clspath.concat(".pdf");
        try {
            PdfWriter writer = new PdfWriter(clspath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PdfFont font = PdfFontFactory.createFont(REGULAR);

            List<DataLabel> dlist = dataList.getDataList();

            int size = dataList.getDataList().size();

            for(int i = 0; i < size; i++){

                DataLabel data = dlist.get(i);

                if (data.getTextType().equals("sign")){

                    Paragraph str = new Paragraph();
                    str.add(data.getContent()).setFont(font).setFontSize(FONT_SIZE_DEFAULT);
                    document.add(str);
                    Image img = createPara();
                    document.add(img);

                } else if (data.getTextType().contains("Check")) {

                    Paragraph str = new Paragraph();

                    if (data.getTitle() != null && !data.getTitle().isEmpty()){
                        str.add(data.getTitle()).setFont(font).setFontSize(FONT_SIZE_DEFAULT);
                    }
                    document.add(str);

                    Table table = createPara(data, data.getTextType());
                    document.add(table);

                } else {
                    Paragraph para = createPara(data);
                    para.setFont(font);
                    document.add(para);
                }
            }
            document.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public Paragraph createPara(DataLabel data){

        Paragraph para = new Paragraph();

        switch (data.getTextType()){

            case "title":

                para.setFontColor(new DeviceRgb(8, 73, 117)).setFontSize(16f);
                para.getAccessibilityProperties().setRole(StandardRoles.H1);
                para.setTextAlignment(TextAlignment.CENTER);
                para.add(data.getContent());
                break;

            case "subtitle":

                para.setFontColor(new DeviceRgb(8, 73, 117)).setFontSize(14f);
                para.getAccessibilityProperties().setRole(StandardRoles.H1);
                para.setTextAlignment(TextAlignment.CENTER);
                para.add(data.getContent());
                break;

            case "text":
                para.setFontSize(FONT_SIZE_DEFAULT);
                para.add(data.getContent());
                break;

            case "subtext":
                para.setFontSize(FONT_SIZE_DEFAULT);
                para.add(data.getContent());
                para.setMarginLeft(20);
                break;

            case "field", "date":
                para.add(data.getContent());
                para.setFontSize(FONT_SIZE_DEFAULT);
                para.add(": " + data.getFieldName());
                break;

        }
        return para;
    }

    public Image createPara(){

        String imageFile = "signatures/signature.png";
        ImageData imgData;
        try {
            imgData = ImageDataFactory.create(imageFile);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        Image img = new Image(imgData);
        img.scaleAbsolute(120, 60);

        return img;
    };

    public Table createPara(DataLabel data, String type) throws IOException {

        Table table = new Table(6);
        boolean checked = false;

        PdfFont font = PdfFontFactory.createFont(REGULAR);


        List<String> valueList = data.getValueList();
        List<String> contentList = data.getContentList();
        List<String> extraField = data.getExtraField();
        List<String> extraList = data.getExtraList();

        int counter = 0;
        int count = 0;
        for (String s : contentList) {

            checked = false;
            ImageData imgData = null;
            Image img = null;


            Cell cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            String cellContent = s;

            for (String str : valueList) {

                if (s.equals(str)) {

                    String imageFile = "Images/Checked.jpg";
                    try {
                        imgData = ImageDataFactory.create(imageFile);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    img = new Image(imgData);
                    checked = true;

                    if (Objects.equals(extraField.get(counter), "True")){
                        cellContent = cellContent.concat(": " + extraList.get(counter) + counter);
                    }
                }
            }

            if (!checked) {
                String imageFile = "Images/Unchecked.jpg";
                try {
                    imgData = ImageDataFactory.create(imageFile);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

                img = new Image(imgData);
            }

            img.scaleAbsolute(10f, 10f);
            Cell cellImg = new Cell();
            cellImg.add(img);
            cellImg.setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cellImg);

            Paragraph para = new Paragraph(cellContent);
            para.setFont(font).setFontSize(FONT_SIZE_DEFAULT);
            cell.add(para);

            table.addCell(cell);

            counter++;
        }

        return table;
    }

}
