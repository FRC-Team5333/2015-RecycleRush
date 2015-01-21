package frc.team5333.core.net;

import edu.wpi.first.wpilibj.RobotDrive;
import frc.team5333.NetIDs;
import frc.team5333.core.RobotImpl;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.lib.RobotData;

import java.io.*;
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
    public Socket client;
    NetworkDispatcher dispatcher;

    public DataInputStream reader;
    public DataOutputStream writer;

    public NetworkedClient(ServerSocket socket, Socket client, NetworkDispatcher dispatcher) {
        this.setName("Controlled Network Client");
        this.server = socket;
        this.client = client;
        this.dispatcher = dispatcher;
    }

    public void run() {
        try {
			RobotImpl.log().info("Client Connected! " + client);
            reader = new DataInputStream(client.getInputStream());
            writer = new DataOutputStream(client.getOutputStream());
            while ((Boolean)RobotData.blackboard.get("network:" + dispatcher.nid + ":alive")) {
                byte id = reader.readByte();

                dispatcher.callback.readLoop(NetIDs.getID(id), this, reader);
            }
        } catch (IOException e) {
            RobotImpl.log().error("Client Disconnected: " + client);
            RobotDriveTracker.setBoth(0D);
        }
    }

    public void replyCommand(String s) throws IOException {
        writer.writeByte(NetIDs.COMMAND_REPLY.id());
        writer.writeUTF(s);
    }

}
