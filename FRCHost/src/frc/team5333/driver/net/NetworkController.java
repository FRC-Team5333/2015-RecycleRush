package frc.team5333.driver.net;

import frc.team5333.NetIDs;
import frc.team5333.driver.gui.GuiDriverPanel;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkController {

    public static Socket socket;
    static String host;
    static int port;
    static DataOutputStream writer;

    public static void setData(String hostname, int aport) {
        host = hostname;
        port = aport;
    }

    public static void connect() {
        try {
            socket = new Socket(host, port);
            GuiDriverPanel.instance.refresh();
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            GuiDriverPanel.instance.refresh();
        }
    }

    public static void sendMessage(NetIDs id, float value) {
        if (socket != null && socket.isConnected()) {
            try {
                writer.writeByte(id.id());
                writer.writeFloat(value);
            } catch (Exception e) {
            	GuiDriverPanel.instance.refresh();
            }
        }
    }

}
