package frc.team5333.driver.net;

import frc.team5333.NetIDs;
import frc.team5333.driver.gui.GuiDriverPanel;

import java.io.IOException;

/**
 * The enumeration for all the network controllers required by the program
 *
 * @author Jaci
 */
public enum EnumNetworkControllers {
    CONTROL("Control", "Co", new NetworkController(5801, new INetReader() {
        @Override
        public void readLoop(NetIDs id, NetworkController client) throws IOException {
        }
    })),
    DEBUG("Debug", "De", new NetworkController(5802, new INetReader() {
        @Override
        public void readLoop(NetIDs id, NetworkController client) throws IOException {
            switch (id) {
                case COMMAND_REPLY:
                    GuiDriverPanel.instance.log(client.reader.readUTF());
                    break;
            }
        }
    }));

    String named;
    String shortname;
    NetworkController controller;

    EnumNetworkControllers(String s, String shortname, NetworkController controller) {
        this.named = s;
        this.shortname = shortname;
        this.controller = controller;
    }

    public static void connectAll() {
        NetworkController.setData(GuiDriverPanel.instance.hostnameField.getText());
        for (EnumNetworkControllers controller : EnumNetworkControllers.values()) {
            controller.controller.connect();
        }
    }

    public boolean connected() {
        controller.connected = controller.socket != null && !controller.socket.isClosed();
        return controller.connected;
    }

    public NetworkController getController() {
        return controller;
    }

    public String getNameFull() {
        return named;
    }

    public String getShortName() {
        return shortname;
    }

}
