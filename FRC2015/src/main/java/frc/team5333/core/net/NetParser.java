package frc.team5333.core.net;

import frc.team5333.NetIDs;
import frc.team5333.core.RobotImpl;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.monitor.PDPMonitor;
import frc.team5333.lib.RobotData;
import frc.team5333.lib.ThreadMonitor;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Parses data coming from clients and acts accordingly
 *
 * @author Jaci
 */
public class NetParser {

    public static void parse(NetIDs id, float data, NetworkedClient client) {
        switch (id) {
            case DRIVE_LEFT:
                RobotDriveTracker.setLeft(data);
                break;
            case DRIVE_RIGHT:
                RobotDriveTracker.setRight(data);
                break;
            case CLAMP:
                RobotDriveTracker.setClamp(data);
                break;
            case LIFT:
                RobotDriveTracker.setLift(data);
                break;
        }
    }

    public static void parse(NetIDs id, String data, NetworkedClient client) throws IOException {
        try {
            switch (id) {
                case COMMAND:
                    String[] split = data.split(" ");
                    if (data.equalsIgnoreCase("ping"))
                        RobotImpl.log().info("pong");
                    if (data.equalsIgnoreCase("pig"))
                        RobotImpl.log().info("pog");

                    if (data.startsWith("stktce"))
                        if (split.length > 1)
                            RobotImpl.log().info(ThreadMonitor.getFormattedStackTrace(split[1]));
                        else
                            RobotImpl.log().info(ThreadMonitor.getFormattedStackTrace(null));

                    if (data.startsWith("pdp"))
                        PDPMonitor.parse(split);

                    if (data.equals("left"))
                        RobotImpl.log().info("Left Motor: " + RobotDriveTracker.getLeft());
                    if (data.equals("right"))
                        RobotImpl.log().info("Right Motor: " + RobotDriveTracker.getRight());

                    if (data.equals("clamp"))
                        RobotImpl.log().info("Clamp Motor: " + RobotDriveTracker.getClamp());
                    if (data.equals("lift"))
                        RobotImpl.log().info("Lift Motor: " + RobotDriveTracker.getLift());

                    break;
            }
        } catch (Exception e) {}
    }

}
