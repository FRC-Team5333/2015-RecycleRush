package frc.team5333.lib;

import edu.wpi.first.wpilibj.*;
import frc.team5333.core.RobotImpl;
import frc.team5333.core.io.IOCallback;
import frc.team5333.core.io.LimitCallback;

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
    NET_CONTROL(1),

    /** DII **/
    LIFT_MAX_LIMIT(2, new LimitCallback(0)),
    LIFT_MIN_LIMIT(3, new LimitCallback(1)),
    ;

    int port;
    DigitalOutput dio;
    DigitalInput dii;

    AnalogOutput aio;
    AnalogInput aii;

    IOCallback callback = null;

    Ports(int portID) {
        this.port = portID;
    }

    Ports(int portID, IOCallback callback) {
        this(portID);
        this.callback = callback;
    }

    public static void tickAll() {
        for (Ports port : Ports.values()) {
            if (port.hasCallback()) {
                port.tickCallback();
            }
        }
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

    public AnalogOutput getAO() {
        if (aio == null) aio = new AnalogOutput(getPort());
        return aio;
    }

    public AnalogInput getAI() {
        if (aii == null) aii = new AnalogInput(getPort());
        return aii;
    }

    public boolean hasCallback() {
        return callback != null;
    }

    public void tickCallback() {
        callback.tick(this);
    }

}
