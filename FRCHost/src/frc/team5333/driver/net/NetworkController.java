package frc.team5333.driver.net;

import frc.team5333.NetIDs;
import frc.team5333.driver.gui.GuiDriverPanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Responsible for handling network connections between the Robot and the
 * Driver Station
 *
 * @author Jaci
 */
public class NetworkController {

    public Socket socket;
    public static String host;
    public int port;
    DataOutputStream writer;
    DataInputStream reader;
    public boolean connected = false;
    INetReader callback;

    public NetworkController(int port, INetReader callback) {
        this.port = port;
        this.callback = callback;
    }

    public static void setData(String hostname) {
        host = hostname;
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            GuiDriverPanel.instance.refresh();
            writer = new DataOutputStream(socket.getOutputStream());
            reader = new DataInputStream(socket.getInputStream());
            connected = true;
            beginRead();
        } catch (IOException e) {
            e.printStackTrace();
            tryClose();
        }
    }

    public void beginRead() {
        final NetworkController controller = this;
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (connected) {
                        byte id = reader.readByte();
                        callback.readLoop(NetIDs.getID(id), controller);
                    }
                } catch (Exception e) {
                    tryClose();
                }
            }
        });
        readThread.start();
    }

    void tryClose() {
        try {
            if (!socket.isClosed())
                socket.close();

            connected = false;

            GuiDriverPanel.instance.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(NetIDs id, float value) {
        if (socket != null && !socket.isClosed()) {
            try {
                writer.writeByte(id.id());
                writer.writeFloat(value);
            } catch (Exception e) {
                tryClose();
            }
        }
    }

    public void sendMessage(NetIDs id, String value) {
        if (socket != null && !socket.isClosed()) {
            try {
                writer.writeByte(id.id());
                writer.writeUTF(value);
            } catch (Exception e) {
                tryClose();
            }
        }
    }

}
