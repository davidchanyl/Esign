package com.asiainsurance.ESign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class swingSignaturePanel extends JFrame {

    private BufferedImage canvasImage;
    private JPanel drawingPanel;
    private Point prevPoint = null;

    public swingSignaturePanel() {
        setTitle("Signature Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize drawing canvas
        canvasImage = new BufferedImage(600, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = canvasImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());
        g2d.dispose();

        // Create and add drawing panel
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvasImage, 0, 0, null);
            }
        };
        drawingPanel.setPreferredSize(new Dimension(600, 300));

        // Add mouse listeners to drawing panel
        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
        	@Override
            public void mouseDragged(MouseEvent e) {
                if (prevPoint != null) {
                    Graphics2D g2 = canvasImage.createGraphics();

                    // Enable anti-aliasing for smoother lines
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                    // Set stroke properties for smooth drawing
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(prevPoint.x, prevPoint.y, e.getX(), e.getY());
                    g2.dispose();
                }
                prevPoint = e.getPoint();
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                prevPoint = null;
            }
        });

        add(drawingPanel, BorderLayout.CENTER);

        // Create buttons
        JButton saveButton = new JButton("Save");
        JButton clearButton = new JButton("Clear");

        saveButton.addActionListener(e -> saveImage());
        clearButton.addActionListener(e -> clearCanvas());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveImage() {
        try {
            File outputFile = new File("signature.png");
            ImageIO.write(canvasImage, "PNG", outputFile);
            JOptionPane.showMessageDialog(this, "Signature saved as 'signature.png'", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save signature.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearCanvas() {
        Graphics2D g2d = canvasImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());
        g2d.dispose();
        drawingPanel.repaint();
    }

    public void openPanel() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public static void main(String[] args) {
        new swingSignaturePanel().openPanel();
    }
}
