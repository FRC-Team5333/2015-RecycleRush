package frc.team5333.driver.net;

/**
 * The enumeration for all the network controllers required by the program
 *
 * @author Jaci
 */
public enum EnumNetworkControllers {
    CONTROL("Control", "Co", new NetworkController(5801)),
    DEBUG("Debug", "De", new NetworkController(5802));

    String named;
    String shortname;
    NetworkController controller;

    EnumNetworkControllers(String s, String shortname, NetworkController controller) {
        this.named = s;
        this.shortname = shortname;
        this.controller = controller;
    }

    public static void connectAll() {
        for (EnumNetworkControllers controller : EnumNetworkControllers.values()) {
            controller.controller.connect();
        }
    }

    public boolean connected() {
        return controller.socket != null && !controller.socket.isClosed();
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
