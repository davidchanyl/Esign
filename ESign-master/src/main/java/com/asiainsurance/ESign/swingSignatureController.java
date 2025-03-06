package com.asiainsurance.ESign;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class swingSignatureController {

    @GetMapping("/open-signature-panel")
    public String openSignaturePanel() {
        // Code to initialize and open the Swing panel
        new Thread(() -> {
            swingSignaturePanel signaturePanel = new swingSignaturePanel();
            signaturePanel.openPanel();
        }).start();
        
        return "Signature panel opened";
    }
   
     
}
