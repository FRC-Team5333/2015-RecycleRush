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
                byte id = reader.readByte();
                float value = reader.readFloat();

                NetParser.parse(NetIDs.getID(id), value);
            }
        } catch (IOException e) {
            RobotImpl.log().error("Client Disconnected: " + client);
        }
    }

}
