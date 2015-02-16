package frc.team5333.core.net;

import frc.team5333.NetIDs;
import frc.team5333.core.RobotImpl;
import frc.team5333.lib.Ports;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * An enumeration for the Network Dispatchers
 *
 * @author Jaci
 */
public enum EnumDispatchers {

    CONTROL("control", 5801, new INetReader() {
        @Override
        public void readLoop(NetIDs id, NetworkedClient client, DataInputStream reader) throws IOException {
            NetParser.parse(id, reader.readFloat(), client);
        }
    }),
    DEBUG("debug", 5802, new INetReader() {
        @Override
        public void readLoop(NetIDs id, NetworkedClient client, DataInputStream reader) throws IOException {
            String utf = reader.readUTF();
            NetParser.parse(id, utf, client);
        }
    });

    NetworkDispatcher dispatch;

    EnumDispatchers(String id, int port, INetReader reader) {
        NetworkDispatcher dispatch = new NetworkDispatcher(id, port, reader);
        this.dispatch = dispatch;
    }

    public NetworkDispatcher get() {
        return dispatch;
    }

    public static void start() {
        for (EnumDispatchers dis : EnumDispatchers.values()) {
            dis.get().start();
        }
    }

    public static void stop() {
        for (EnumDispatchers dis : EnumDispatchers.values()) {
            try {
                dis.get().stopNetwork();
            } catch (IOException e) {
                NetParser.netLogger.exception(e);
            }
        }
    }

}
