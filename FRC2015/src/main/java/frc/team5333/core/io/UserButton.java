package frc.team5333.core.io;

import edu.wpi.first.wpilibj.Utility;
import jaci.openrio.toast.lib.state.RobotState;
import jaci.openrio.toast.lib.state.StateListener;

/**
 * Handles data being sent from the User Button on the RoboRIO
 *
 * @author Jaci
 */
public class UserButton implements StateListener.Ticker {

    public static boolean pressed;
    public static long pressedTime;
    static long pressStart;

    public static void poll() {
        boolean newState = Utility.getUserButton();
        if (newState != pressed) {
            pressed = newState;
            onChange();
        }

        if (pressed) {
            pressedTime = System.currentTimeMillis() - pressStart;
            pressTick();
        }
    }

    public static void pressTick() {
    }

    public static void onChange() {
        if (pressed)
            pressStart = System.currentTimeMillis();
    }

    @Override
    public void tickState(RobotState state) {
        poll();
    }

}
