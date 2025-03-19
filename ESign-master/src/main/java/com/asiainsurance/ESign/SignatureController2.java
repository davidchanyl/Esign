package com.asiainsurance.ESign;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;

@RestController
public class SignatureController2 {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload-signature")
    public ResponseEntity<Map<String, String>> uploadSignature(@RequestBody Map<String, String> requestData) throws IOException {
        String base64Image = requestData.get("image").split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        
        String fileName = "signature_" + System.currentTimeMillis() + ".png";
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageBytes);

        String imageUrl = "/uploads/" + fileName;

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        return ResponseEntity.ok(response);
    }
}
