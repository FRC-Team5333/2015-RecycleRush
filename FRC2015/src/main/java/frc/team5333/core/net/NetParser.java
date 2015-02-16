package frc.team5333.core.net;

import frc.team5333.NetIDs;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.net.command.CommandRegistry;
import frc.team5333.lib.ThreadMonitor;
import jaci.openrio.toast.lib.log.Logger;

import java.io.IOException;

/**
 * Parses data coming from clients and acts accordingly
 *
 * @author Jaci
 */
public class NetParser {
    
    public static Logger netLogger = new Logger("Network", Logger.ATTR_DEFAULT);

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
                        netLogger.info("pong");
                    if (data.equalsIgnoreCase("pig"))
                        netLogger.info("pog");

                    if (data.startsWith("stktce"))
                        if (split.length > 1)
                            netLogger.info(ThreadMonitor.getFormattedStackTrace(split[1]));
                        else
                            netLogger.info(ThreadMonitor.getFormattedStackTrace(null));

                    if (data.equals("left"))
                        netLogger.info("Left Motor: " + RobotDriveTracker.getLeft());
                    if (data.equals("right"))
                        netLogger.info("Right Motor: " + RobotDriveTracker.getRight());

                    if (data.equals("clamp"))
                        netLogger.info("Clamp Motor: " + RobotDriveTracker.getClamp());
                    if (data.equals("lift"))
                        netLogger.info("Lift Motor: " + RobotDriveTracker.getLift());

                    CommandRegistry.parse(data, client);

                    break;
            }
        } catch (Exception e) {
            netLogger.exception(e);
        }
    }

}
