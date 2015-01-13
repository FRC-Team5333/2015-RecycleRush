package frc.team5333.core.net;

import frc.team5333.lib.RobotData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The network dispatcher for the Control Station. This is mostly
 * used for IO of control schemes for the robot
 *
 * @author Jaci
 */
public class NetworkDispatcher extends Thread {

    public NetworkDispatcher() {
        this.setName("Network Control Dispatcher");
    }

    public void run() {
        try {
            RobotData.blackboard.putIfAbsent("network:control:port", 5801);
            RobotData.blackboard.putIfAbsent("network:control:alive", true);
            ServerSocket socket = new ServerSocket((int)RobotData.blackboard.get("network:control:port"));

            while ((Boolean)RobotData.blackboard.get("network:control:alive")) {
                Socket clientS = socket.accept();
                NetworkedClient client = new NetworkedClient(socket, clientS);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
