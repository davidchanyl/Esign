package com.asiainsurance.ESign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class SignatureController {

    private static final String UPLOAD_DIR = "signatures/";

    @PostMapping("/upload-signature")
    public ResponseEntity<Map<String, String>> uploadSignature(@RequestBody Map<String, String> requestData) throws IOException {
        String base64Image = requestData.get("image").split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        
        String fileName = "signature_" + System.currentTimeMillis() + ".png";
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageBytes);

        String imageUrl = "/signatures/" + fileName;

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearSignature() {
        File outputFile = new File(UPLOAD_DIR + "signature.png");
        if (outputFile.exists() && outputFile.delete()) {
            return ResponseEntity.ok("Signature cleared successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clear signature.");
        }
    }

    // Inner class to handle the JSON request
    static class ImageRequest {
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}