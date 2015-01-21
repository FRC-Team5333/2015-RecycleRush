package frc.team5333.core.io;

import edu.wpi.first.wpilibj.Utility;
import frc.team5333.core.RobotImpl;
import frc.team5333.lib.RobotState;
import frc.team5333.lib.StateListener;

public class UserButton implements StateListener {

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

        RobotImpl.log().info("User Button Change. Current state: " + pressed);
    }

    @Override
    public void tickState(RobotState state) {
        poll();
    }

    @Override
    public void transitionState(RobotState state, RobotState oldState) {
    }
}
