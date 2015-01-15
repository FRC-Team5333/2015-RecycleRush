package frc.team5333.core.net;

import frc.team5333.NetIDs;
import frc.team5333.core.drive.RobotDriveTracker;

/**
 * Parses data coming from clients and acts accordingly
 *
 * @author Jaci
 */
public class NetParser {

    public static void parse(NetIDs id, float data) {
        switch (id) {
            case DRIVE_LEFT:
                RobotDriveTracker.setLeft(data);
                break;
            case DRIVE_RIGHT:
                RobotDriveTracker.setRight(data);
                break;
        }
    }

}
