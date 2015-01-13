package frc.team5333;

/**
 * A shared collection of NetIDs that is kept constant between applications
 * communicating with the Robot.
 *
 * The implementation uses Bytes instead of Floats to save on transmission
 * cost and increase efficiency, thus using less data in order to react.
 *
 * @author Jaci
 */
public enum NetIDs {
    DRIVE_RIGHT(0),
    DRIVE_LEFT(1),
    ;

    byte id;

    NetIDs(byte id) {
        this.id = id;
    }

    NetIDs(int id) {
        this((byte)id);
    }

    public byte id() {
        return id;
    }

    public static NetIDs getID(int n) {
        for (NetIDs id : NetIDs.values())
            if (id.id() == (byte)n) return id;
        return null;
    }
}
