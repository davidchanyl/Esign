package com.asiainsurance.ESign;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PdfController {

    @GetMapping(value = "/pdfform")
    public String pdfform(@RequestParam(name="name") String name, Model model) {
        DataLoader dataLoader = new DataLoader();
        model.addAttribute("textContent",dataLoader.getPdfContent(name));
        model.addAttribute("counter", new Counter());
        model.addAttribute("titleName", name);

        return "pdfform";
    }
     
}
