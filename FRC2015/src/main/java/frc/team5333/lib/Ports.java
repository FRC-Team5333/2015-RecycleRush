package frc.team5333.lib;

import edu.wpi.first.wpilibj.*;
import frc.team5333.core.RobotImpl;

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
    CLAMP(3),
    LIFT(2),

    /** DII **/
    LIFT_MAX_LIMIT(2),
    LIFT_MIN_LIMIT(3),
    CLAMP_0(0),
    CLAMP_1(1),
    ;

    int port;
    DigitalOutput dio;
    DigitalInput dii;

    AnalogOutput aio;
    AnalogInput aii;

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

    public AnalogOutput getAO() {
        if (aio == null) aio = new AnalogOutput(getPort());
        return aio;
    }

    public AnalogInput getAI() {
        if (aii == null) aii = new AnalogInput(getPort());
        return aii;
    }

}
