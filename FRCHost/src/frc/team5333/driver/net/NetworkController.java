package frc.team5333.driver.net;

import frc.team5333.NetIDs;
import frc.team5333.driver.gui.GuiDriverPanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

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

    boolean lock;

    int timeoutRemain;

    public NetworkController(int port, INetReader callback) {
        this.port = port;
        this.callback = callback;
    }

    public static boolean ping() throws IOException, InterruptedException {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows ? "-n" : "-c", "1", host);
        Process process = processBuilder.start();

        boolean e = process.waitFor(1000, TimeUnit.MILLISECONDS);
        return e && process.exitValue() == 0;

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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        timeoutRemain = 5;
                        while (true) {
                            timeoutRemain--;
                            if (timeoutRemain == 0)
                                tryClose();

                            periodicTest();
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        System.err.println("Ping Failed!");
                    };
                }
            }).start();
        } catch (IOException e) {
            tryClose();
        }
    }

    public void periodicTest() throws IOException {
        if (!lock) {
            writer.writeByte(NetIDs.PING.id());
        }
    }

    public void beginRead() {
        final NetworkController controller = this;
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        byte id = reader.readByte();
                        if (id == NetIDs.PING.id())
                            writer.writeByte(NetIDs.PONG.id());
                        else if (id == NetIDs.PONG.id())
                            timeoutRemain = 5;
                        else
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
            connected = false;
            GuiDriverPanel.instance.refresh();

            if (!socket.isClosed())
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(NetIDs id, float value) {
        if (socket != null && !socket.isClosed()) {
            try {
                lock = true;
                writer.writeByte(id.id());
                writer.writeFloat(value);
                lock = false;
            } catch (Exception e) {
                tryClose();
            }
        }
    }

    public void sendMessage(NetIDs id, String value) {
        if (socket != null && !socket.isClosed()) {
            try {
                lock = true;
                writer.writeByte(id.id());
                writer.writeUTF(value);
                lock = false;
            } catch (Exception e) {
                tryClose();
            }
        }
    }

}
