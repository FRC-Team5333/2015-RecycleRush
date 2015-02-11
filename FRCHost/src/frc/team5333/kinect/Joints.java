package frc.team5333.kinect;

public enum Joints {
    HEAD("Head"),
    SHOULDER_CENTRE("ShoulderCenter", HEAD),
    SHOULDER_LEFT("ShoulderLeft", SHOULDER_CENTRE),
    SHOULDER_RIGHT("ShoulderRight", SHOULDER_CENTRE),
    SPINE("Spine", SHOULDER_CENTRE),
    HIP_CENTRE("HipCenter", SPINE),
    HIP_LEFT("HipLeft", HIP_CENTRE),
    HIP_RIGHT("HipRight", HIP_CENTRE),
    ELBOW_LEFT("ElbowLeft", SHOULDER_LEFT),
    ELBOW_RIGHT("ElbowRight", SHOULDER_RIGHT),
    WRIST_LEFT("WristLeft", ELBOW_LEFT),
    WRIST_RIGHT("WristRight", ELBOW_RIGHT),
    HAND_LEFT("HandLeft", WRIST_LEFT),
    HAND_RIGHT("HandRight", WRIST_RIGHT),
    KNEE_LEFT("KneeLeft", HIP_LEFT),
    KNEE_RIGHT("KneeRight", HIP_RIGHT),
    ANKLE_LEFT("AnkleLeft", KNEE_LEFT),
    ANKLE_RIGHT("AnkleRight", KNEE_RIGHT),
    FOOT_LEFT("FootLeft", ANKLE_LEFT),
    FOOT_RIGHT("FootRight", ANKLE_RIGHT);

    String kID;
    Joints connect;

    Joints(String id) {
        kID = id;
    }

    Joints(String id, Joints connect) {
        this(id);
        this.connect = connect;
    }

    public String getID() {
        return kID;
    }

    public boolean hasParent() {
        return connect != null;
    }

    public Joints getParent() {
        return connect;
    }

    public static Joints get(String name) {
        for (Joints j : Joints.values())
            if (j.getID().equals(name))
                return j;
        return null;
    }
}
