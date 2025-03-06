package com.asiainsurance.ESign;


import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SignatureController {

    private final int WIDTH = 600;
    private final int HEIGHT = 300;
    private BufferedImage canvas;

    public SignatureController() {
        // Initialize the canvas
        canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.dispose();
    }

    @PostMapping("/draw")
    public void draw(@RequestBody List<DrawEvent> drawEvents) {
        Graphics2D g2d = canvas.createGraphics();
        for (int i = 0; i < drawEvents.size(); i++) {
            DrawEvent event = drawEvents.get(i);
            //g2d.setColor(Color.decode(event.getColor()));
            //g2d.setStroke(new BasicStroke(event.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // If this is not the first event, connect to the previous point
            if (i > 0) {
                DrawEvent prevEvent = drawEvents.get(i - 1);
                g2d.drawLine(prevEvent.getEndX(), prevEvent.getEndY(), event.getStartX(), event.getStartY());
            }

            // Draw the current segment
            g2d.drawLine(event.getStartX(), event.getStartY(), event.getEndX(), event.getEndY());
        }      

        g2d.dispose();
    }

    @GetMapping("/image")
    public String getImage() throws IOException {
        // Save the image to a file
        File outputFile = new File("signature.png");
        ImageIO.write(canvas, "PNG", outputFile);

        // Return Base64-encoded image data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(canvas, "PNG", baos);
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    @DeleteMapping("/clear")
    public void clearCanvas() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.dispose();
    }
}
