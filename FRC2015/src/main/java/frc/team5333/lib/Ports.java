package frc.team5333.lib;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * An enumeration of all the ports allocated on the RoboRio
 * and their appropriate purpose
 *
 * @author Jaci
 */
public enum Ports {
    /** PWM **/
    LEFT_DRIVE(1),
    RIGHT_DRIVE(0),
    CLAMP(2),
    LIFT(3),

    /** DIO **/
    NET_DEBUG(0),
    NET_CONTROL(1)
    ;

    int port;
    DigitalOutput dio;
    DigitalInput dii;

    Ports(int portID) {
        this.port = portID;
    }

    /**
     * Get the port number for the allocated port
     */
    public int getPort() {
        return port;
    }

    public DigitalOutput getDO() {
        if (dio == null) dio = new DigitalOutput(getPort());
        return dio;
    }

    public DigitalInput getDI() {
        if (dii == null) dii = new DigitalInput(getPort());
        return dii;
    }

}
