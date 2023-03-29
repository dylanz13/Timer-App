package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Draw extends JPanel {
    private JTextField time;
    private int totTime;
    Shape progress;

    public Draw(String str) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(19,17,0,0);

        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 40;
        c.ipady = 20;

        time = new JTextField(4);
        time.setText(str);
        time.setEditable(false);
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setBorder(BorderFactory.createLineBorder(new Color(56,52,44), 1));
        this.add(time, c);
        totTime = 0;
    }

    public JTextField getTimeTextField() {
        return time;
    }

    public void setTotTime(int i) {
        totTime = i;
    }

    public void updateProgress() throws Exception {
        Arc2D arc1 = new Arc2D.Double(20, 20,160,160,0,
                ((double) TimerApp.getTimeFromString(time.getText().split(":",3)) / totTime) * -360, Arc2D.PIE);
        Arc2D arc2 = new Arc2D.Double(40, 40, 120, 120, 0,
                ((double) TimerApp.getTimeFromString(time.getText().split(":",3)) / totTime) * -360, Arc2D.PIE);
        Area area = new Area(arc1);
        area.subtract(new Area(arc2));
        progress = area;
    }

    public void resetProgress() {
        progress = null;
    }

    public void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Shape ring = createRingShape(100, 100, 80, 20);

        if (progress != null) {
            g2D.setColor(new Color(255, 127, 80));
            g2D.fill(ring);
            g2D.draw(ring);
            g2D.setColor(new Color(56,52,44));
            g2D.fill(progress);
            g2D.draw(progress);
            g2D.setColor(new Color(255, 127, 80));
            g2D.setStroke(new BasicStroke(5));
            try {
                g2D.drawLine(100,100, 100 + (int) (79 * Math.cos(getRadians())),
                        100 + (int) (79 * Math.sin(getRadians())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            g2D.setColor(new Color(56,52,44));
            g2D.fill(ring);
            g2D.draw(ring);
        }
    }

    private double getRadians() throws Exception {
        return Math.toRadians(((double) TimerApp.getTimeFromString(time.getText().split(":",3)) / totTime)
                * 360 - 1);
    }

    private static Shape createRingShape(double centerX, double centerY, double outerRadius, double thickness) {
        Ellipse2D outer = new Ellipse2D.Double(
                centerX - outerRadius,
                centerY - outerRadius,
                outerRadius + outerRadius,
                outerRadius + outerRadius);
        Ellipse2D inner = new Ellipse2D.Double(
                centerX - outerRadius + thickness,
                centerY - outerRadius + thickness,
                outerRadius + outerRadius - thickness - thickness,
                outerRadius + outerRadius - thickness - thickness);
        Area area = new Area(outer);
        area.subtract(new Area(inner));
        return area;
    }
}
