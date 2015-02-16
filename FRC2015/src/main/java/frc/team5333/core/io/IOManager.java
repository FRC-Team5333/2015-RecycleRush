package frc.team5333.core.io;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;
import frc.team5333.lib.Ports;
import jaci.openrio.toast.lib.state.RobotState;
import jaci.openrio.toast.lib.state.StateListener;

import java.util.HashMap;

/**
 * Manage IO that isn't covered by other sections of code
 *
 * @author Jaci
 */
public class IOManager {

    public static void setDigital(Ports port, boolean state) {
        port.getDO().set(state);
    }

    public static void init() {
    }


}
