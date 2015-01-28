package frc.team5333.core.io;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import frc.team5333.lib.Ports;
import frc.team5333.lib.RobotState;
import frc.team5333.lib.StateListener;

import java.util.HashMap;

/**
 * Manage IO that isn't covered by other sections of code
 *
 * @author Jaci
 */
public class IOManager implements StateListener {

    public static void setDigital(Ports port, boolean state) {
        port.getDO().set(state);
    }

    @Override
    public void tickState(RobotState state) {
        Ports.tickAll();
    }

    @Override
    public void transitionState(RobotState state, RobotState oldState) {
    }
}
