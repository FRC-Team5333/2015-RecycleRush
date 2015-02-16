package frc.team5333.core.net;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team5333.lib.Ports;
import frc.team5333.lib.RobotData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The network dispatcher for the Control Station. This is mostly
 * used for IO of control schemes for the robot
 *
 * @author Jaci
 */
public class NetworkDispatcher extends Thread {

    String nid;
    int port;
    INetReader callback;
    ServerSocket socket;

    public ArrayList<NetworkedClient> connectedClients = new ArrayList<NetworkedClient>();

    public NetworkDispatcher(String id, int port, INetReader callback) {
        this.setName("Network-" + id + "-Dispatcher");
        this.nid = id;
        this.port = port;
        this.callback = callback;
    }

    public void run() {
        try {
            RobotData.blackboard.putIfAbsent("network:" + nid + ":port", port);
            RobotData.blackboard.putIfAbsent("network:" + nid + ":alive", true);
            RobotData.blackboard.put("network:" + nid + ":dispatch", this);
            ServerSocket socket = new ServerSocket((int)RobotData.blackboard.get("network:" + nid + ":port"));

            while ((Boolean)RobotData.blackboard.get("network:" + nid + ":alive")) {
                Socket clientS = socket.accept();
                NetworkedClient client = new NetworkedClient(socket, clientS, this);
                connectedClients.add(client);
                client.start();
            }
        } catch (IOException e) {
            NetParser.netLogger.exception(e);
        }
    }

    public void broadcast(String s) {
        for (NetworkedClient socket : connectedClients) {
            try {
                if (socket.client != null && !socket.client.isClosed()) {
                    socket.replyCommand(s);
                }
            } catch (Exception e) {
            }
        }
    }

    public void stopNetwork() throws IOException {
        RobotData.blackboard.putIfAbsent("network:" + nid + ":alive", false);
        if (socket != null)
            socket.close();
    }
}
