package com.asiainsurance.ESign;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class canvasController {

    @GetMapping(value = "/canvas")
    public String canvas() {


        return "canvas";
    }
     
}
