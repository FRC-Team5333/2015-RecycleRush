package frc.team5333.core.drive;

import groovy.lang.GroovyObject;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.core.loader.groovy.GroovyLoader;
import jaci.openrio.toast.lib.state.RobotState;
import jaci.openrio.toast.lib.state.StateListener;

public class AutonomousDriver implements StateListener.Transition {

    public static String id = "default";

    static GroovyObject autoTracker;

    public static void init() {
        try {
            autoTracker = GroovyLoader.getObject("Routines");
            StateTracker.addTransition(new AutonomousDriver());
        } catch (Exception e) {}
    }

    @Override
    public void transitionState(RobotState state, RobotState oldState) {
        try {
            if (state == RobotState.AUTONOMOUS) {
                GroovyObject context = (GroovyObject) autoTracker.invokeMethod("getContext", id);
                context.invokeMethod("setControllers", RobotDriveTracker.getControllers());
                context.invokeMethod("startPlayback", null);
            } else {
                autoTracker.invokeMethod("stopAll", null);
                RobotDriveTracker.setAll(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
