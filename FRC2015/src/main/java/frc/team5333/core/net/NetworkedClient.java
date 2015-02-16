package frc.team5333.core.net;

import frc.team5333.NetIDs;
import frc.team5333.core.RobotImpl;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.io.IOManager;
import frc.team5333.lib.Ports;
import frc.team5333.lib.RobotData;
import jaci.openrio.toast.lib.log.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    boolean lock;

    int timeoutRemain;

    public NetworkedClient(ServerSocket socket, Socket client, NetworkDispatcher dispatcher) {
        this.setName("NetClient");
        this.server = socket;
        this.client = client;
        this.dispatcher = dispatcher;
    }

    public void run() {
        try {
            NetParser.netLogger.info("Client Connected! " + client);
            reader = new DataInputStream(client.getInputStream());
            writer = new DataOutputStream(client.getOutputStream());

            replyCommand("***START BACKLOG***");

            for (String s : Logger.backlog)
                replyCommand("*" + s);

            replyCommand("***END BACKLOG***");

            while ((Boolean) RobotData.blackboard.get("network:" + dispatcher.nid + ":alive")) {
                byte id = reader.readByte();

                dispatcher.callback.readLoop(NetIDs.getID(id), this, reader);
            }
        } catch (IOException e) {
            NetParser.netLogger.exception(e);
            disconnect();
        }
    }

    public void disconnect() {
        RobotImpl.log().error("Client Disconnected: " + client);
        RobotDriveTracker.setAll(0D);

        if (!client.isClosed())
            try {
                client.close();
            } catch (IOException e) {
            }
    }

    public void replyCommand(String s) throws IOException {
        lock = true;
        if (writer != null) {
            writer.writeByte(NetIDs.COMMAND_REPLY.id());
            writer.writeUTF(s);
        }
        lock = false;
    }

}
