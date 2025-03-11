package com.asiainsurance.ESign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Base64;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class SignatureController {

    private static final String UPLOAD_DIR = "signatures/";

    @PostMapping("/upload-signature")
    public ResponseEntity<String> uploadSignature(@RequestBody ImageRequest request) {
        try {
            // Decode Base64 image
            String base64Image = request.getImage().replace("data:image/png;base64,", "");
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Ensure the directory exists
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the image file
            File outputFile = new File(UPLOAD_DIR + "signature.png");
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(imageBytes);
            }

            return ResponseEntity.ok("Signature saved successfully at " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving signature: " + e.getMessage());
        }
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