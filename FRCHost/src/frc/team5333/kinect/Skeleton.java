package frc.team5333.kinect;

import java.util.HashMap;
import java.util.Map;

public class Skeleton {

    private boolean active;

    private HashMap<Joints, JointPosition> jointOffsets;
    private HashMap<Joints, JointPosition> jointScales;
    private HashMap<Joints, JointPosition> rawPositions;

    public Skeleton() {
        active = false;
        jointOffsets = new HashMap<>();
        jointScales = new HashMap<>();
        rawPositions = new HashMap<>();
    }

    public void setActive(boolean state) {
        this.active = state;
    }

    public boolean active() {
        return active;
    }

    public void addJoint(Joints joint, float x, float y, float z) {
        rawPositions.put(joint, new JointPosition(x, y, z));
    }

    public void setOffset(Joints joint, float x, float y, float z) {
        jointOffsets.put(joint, new JointPosition(x, y, z));
    }

    public void calibrateOffset() {
        for (Map.Entry<Joints, JointPosition> entry : rawPositions.entrySet()) {
            jointOffsets.put(entry.getKey(), entry.getValue());
        }
    }

    public void calibrateScale() {
        for (Map.Entry<Joints, JointPosition> entry : rawPositions.entrySet()) {
            if (entry.getKey().equals(Joints.HAND_LEFT) || entry.getKey().equals(Joints.HAND_RIGHT))
                jointScales.put(entry.getKey(), entry.getValue());
        }
    }

    public JointPosition getPosCalibrated(Joints joint) {
        JointPosition raw = rawPositions.get(joint);
        float x = raw.x;
        float y = raw.y;
        float z = raw.z;
        JointPosition offset = jointOffsets.get(joint);
        if (offset != null) {
            x -= offset.x;
            y -= offset.y;
            z -= offset.z;
        }

        JointPosition scale = jointScales.get(joint);
        if (scale != null) {
            y = y / scale.y;
        }

        return new JointPosition(x, y, z);
    }

    public JointPosition getPosRaw(Joints joint) {
        return rawPositions.get(joint);
    }

}