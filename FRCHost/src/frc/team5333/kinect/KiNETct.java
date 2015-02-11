package frc.team5333.kinect;

import frc.team5333.driver.gui.GuiDriverPanel;

import javax.swing.*;
import java.io.Console;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KiNETct extends Thread {

    public static HashMap<Integer, Skeleton> skeletons = new HashMap<Integer, Skeleton>();

    JointGUI gui;

    public KiNETct(JointGUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(GuiDriverPanel.instance.kinectHostname.getText(), 5805);
            ArrayList<Integer> updatedKeys = new ArrayList<Integer>();
            gui.connected(true);
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            while (true) {
                byte l = dis.readByte();
                byte[] read = new byte[l];
                dis.readFully(read);
                String message = new String(read);

                if (message.equals("s")) {
                    updatedKeys.clear();
                    for (Map.Entry<Integer, Skeleton> entry : skeletons.entrySet()) {
                        updatedKeys.add(entry.getKey());
                    }
                } else if (message.equals("e")) {
                    for (int val : updatedKeys) {
                        skeletons.remove(val);
                    }
                    gui.refresh();
                } else {
                    String[] split = message.split(":");
                    int skeletonID = Integer.parseInt(split[0]);

                    if (updatedKeys.contains(skeletonID))
                        updatedKeys.remove((Object)skeletonID);

                    Joints jointType = Joints.get(split[1]);

                    Skeleton skeleton = new Skeleton();
                    skeletons.putIfAbsent(skeletonID, skeleton);

                    skeleton = skeletons.get(skeletonID);

                    skeleton.addJoint(jointType, Float.parseFloat(split[2]), Float.parseFloat(split[3]), Float.parseFloat(split[4]));
                }
            }
        } catch (Exception e) {
            gui.connected(false);
        }

    }

    public static void calibrateOffsetAll() {
        for (Skeleton skeleton : skeletons.values()) {
            skeleton.calibrateOffset();
        }
    }

    public static void calibrateScaleAll() {
        for (Skeleton skeleton : skeletons.values()) {
            skeleton.calibrateScale();
        }
    }

    public static void selectActive() {
        for (Skeleton skeleton : skeletons.values()) {
            JointPosition lHand = skeleton.getPosCalibrated(Joints.HAND_LEFT);
            JointPosition rHand = skeleton.getPosCalibrated(Joints.HAND_RIGHT);

            if (lHand != null && rHand != null) {
                if (lHand.y > 0.5 && rHand.y > 0.5) {
                    skeleton.setActive(true);
                    return;
                }
            }

            skeleton.setActive(false);
        }
    }

}
