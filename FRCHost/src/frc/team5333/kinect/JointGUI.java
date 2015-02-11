package frc.team5333.kinect;

import frc.team5333.driver.control.drive.DriveController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class JointGUI extends JPanel {

    public JointGUI() {
        setLayout(null);

        this.setBackground(new Color(80, 0, 0));
        this.setEnabled(true);
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;

        paintController(gr);
    }

    public void connected(boolean s) {
        this.setBackground(s ? new Color(0, 0, 0) : new Color(80, 0, 0));
    }

    public void refresh() {
        repaint();

        boolean dominantFound = false;

        for (Map.Entry<Integer, Skeleton> skeletons : KiNETct.skeletons.entrySet()) {
            Skeleton skele = skeletons.getValue();
            if (skele.active()) {
                JointPosition lHand = skele.getPosCalibrated(Joints.HAND_LEFT);
                JointPosition rHand = skele.getPosCalibrated(Joints.HAND_RIGHT);

                if (lHand != null && rHand != null) {
                    DriveController.setLeft(lHand.y * 1.5F);
                    DriveController.setRight(rHand.y * 1.5F);
                    dominantFound = true;
                }
            }
        }

        if (!dominantFound) {
            DriveController.setLeft(0);
            DriveController.setRight(0);
        }
    }

    public void paintController(Graphics2D gr) {
        try {
            gr.setFont(new Font("Arial", 0, 20));
            int scale = 100;
            for (Map.Entry<Integer, Skeleton> skeletons : KiNETct.skeletons.entrySet()) {
                for (Joints joint : Joints.values()) {
                    Skeleton skele = skeletons.getValue();
                    boolean active = skele.active();

                    gr.setColor(active ? new Color(100, 255, 100) : new Color(255, 100, 100, 150));

                    JointPosition pos = skele.getPosRaw(joint);

                    int x = (int) (pos.x * scale) + scale;
                    int y = (int) (pos.y * -1 * scale) + 130;
                    gr.fillOval(x, y, 5, 5);

                    if (joint.hasParent()) {
                        JointPosition parentPos = skele.getPosRaw(joint.getParent());

                        int px = (int) (parentPos.x * scale) + scale;
                        int py = (int) (parentPos.y * -1 * scale) + 130;
                        gr.setColor(active ? new Color(100, 100, 255) : new Color(255, 255, 100, 170));
                        gr.drawLine(x, y, px, py);
                    }
                }
            }
        } catch (Exception e) {}
    }

}