package frc.team5333.lib;

/**
 * An enumeration of all the ports allocated on the RoboRio
 * and their appropriate purpose
 *
 * @author Jaci
 */
public enum Ports {
    LEFT_DRIVE(1),
    RIGHT_DRIVE(0),
    ;

    int port;

    Ports(int portID) {
        this.port = portID;
    }

    /**
     * Get the port number for the allocated port
     */
    public int getPort() {
        return port;
    }

}
