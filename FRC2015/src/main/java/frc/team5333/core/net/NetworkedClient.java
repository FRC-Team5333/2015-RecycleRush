package frc.team5333.core.net;

import edu.wpi.first.wpilibj.RobotDrive;
import frc.team5333.NetIDs;
import frc.team5333.core.RobotImpl;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.io.IOManager;
import frc.team5333.lib.Ports;
import frc.team5333.lib.RobotData;
import frc.team5333.lib.logger.Logger;

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

    boolean lock;

    int timeoutRemain;

    Ports signal;

    public NetworkedClient(ServerSocket socket, Socket client, NetworkDispatcher dispatcher, Ports signal) {
        this.setName("NetClient");
        this.server = socket;
        this.client = client;
        this.dispatcher = dispatcher;
        this.signal = signal;
    }

    public void run() {
        try {
			RobotImpl.log().info("Client Connected! " + client);
            reader = new DataInputStream(client.getInputStream());
            writer = new DataOutputStream(client.getOutputStream());

            if (signal != null)
                IOManager.setDigital(signal, true);

            replyCommand("***START BACKLOG***");

            for (String s : Logger.backlog)
                replyCommand("*" + s);

            replyCommand("***END BACKLOG***");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        timeoutRemain = 5;
                        while (true) {
                            timeoutRemain--;
                            if (timeoutRemain == 0)
                                client.close();

                            periodicTest();
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        disconnect();
                    }
                }
            }).start();

            while ((Boolean)RobotData.blackboard.get("network:" + dispatcher.nid + ":alive")) {
                byte id = reader.readByte();

                if (id == NetIDs.PING.id())
                    writer.writeByte(NetIDs.PONG.id());
                else if (id == NetIDs.PONG.id())
                    timeoutRemain = 5;
                else
                    dispatcher.callback.readLoop(NetIDs.getID(id), this, reader);
            }
        } catch (IOException e) {
            disconnect();
        }
    }

    public void disconnect() {
        RobotImpl.log().error("Client Disconnected: " + client);
        RobotDriveTracker.setAll(0D);

        if (!client.isClosed())
            try {
                client.close();
            } catch (IOException e) {}

        if (signal != null)
            IOManager.setDigital(signal, false);
    }

    public void replyCommand(String s) throws IOException {
        lock = true;
        writer.writeByte(NetIDs.COMMAND_REPLY.id());
        writer.writeUTF(s);
        lock = false;
    }

    public void periodicTest() throws IOException {
        if (!lock)
            writer.writeByte(NetIDs.PING.id());
    }

}
