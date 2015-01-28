package frc.team5333.driver.net;

import frc.team5333.NetIDs;

import java.io.IOException;

public interface INetReader {

    public void readLoop(NetIDs id, NetworkController client) throws IOException;

}
