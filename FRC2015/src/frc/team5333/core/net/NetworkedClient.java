package frc.team5333.core.net;

import edu.wpi.first.wpilibj.RobotDrive;
import frc.team5333.NetIDs;
import frc.team5333.core.RobotImpl;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.lib.RobotData;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static frc.team5333.NetIDs.DRIVE_LEFT;

/**
 * A thread for each client connected to the robot. This is used
 * for parsing data from the DriverStation software we run
 * to use custom control schemes and report extra data
 *
 * @author Jaci
 */
public class NetworkedClient extends Thread {

    ServerSocket server;
    Socket client;

    public NetworkedClient(ServerSocket socket, Socket client) {
        this.setName("Controlled Network Client");
        this.server = socket;
        this.client = client;
    }

    public void run() {
        try {
			RobotImpl.log().info("Client Connected! " + client);
            DataInputStream reader = new DataInputStream(client.getInputStream());
            while ((Boolean)RobotData.blackboard.get("network:control:alive")) {
//                String line = reader.readLine().trim();
//                String[] data = line.split(":");
//
//                //TODO TEMPORARY
//                if (data[0].equalsIgnoreCase("leftThrottle"))
//                    RobotDriveTracker.setLeft(Double.parseDouble(data[1]));
//                else if (data[0].equalsIgnoreCase("rightThrottle"))
//                    RobotDriveTracker.setRight(Double.parseDouble(data[1]));
                byte id = reader.readByte();
                byte data = reader.readByte();

                float value = (float)data / 10F;

                switch (NetIDs.getID(id)) {
                    case DRIVE_LEFT:
                        RobotDriveTracker.setLeft(value);
                        break;
                    case DRIVE_RIGHT:
                        RobotDriveTracker.setRight(value);
                        break;
                }
            }
        } catch (IOException e) {
            RobotData.blackboard.put("network:control:alive", false);
            RobotImpl.log().error("Client Disconnected: " + client);
        }
    }

}
