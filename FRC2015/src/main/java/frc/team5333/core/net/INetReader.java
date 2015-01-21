package frc.team5333.core.net;

import frc.team5333.NetIDs;

import java.io.DataInputStream;
import java.io.IOException;

public interface INetReader {

    public void readLoop(NetIDs id, NetworkedClient client, DataInputStream reader) throws IOException;

}
