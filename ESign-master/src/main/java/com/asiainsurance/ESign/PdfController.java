package com.asiainsurance.ESign;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class PdfController {

    @GetMapping(value = "/pdfform")
    public ModelAndView pdfform(Model model) {

        ModelAndView mav = new ModelAndView("pdfform");
        model.addAttribute("titleName", "sample");
        DataList dataList = new DataList("sample");
        mav.addObject("dataList", dataList);

        return mav;
    }

    @PostMapping("/pdfform")
    public String pdfformSubmit(Model model, @ModelAttribute DataList dataList) throws IOException {

        PdfCreator pdfCreator = new PdfCreator();

        pdfCreator.createPdf("sample", dataList);



        return "results";
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> download(String param) throws IOException {
        String dirpath = "";
        String clspath = dirpath.concat("sample.pdf");
        File file = ResourceUtils.getFile(clspath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sample.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
