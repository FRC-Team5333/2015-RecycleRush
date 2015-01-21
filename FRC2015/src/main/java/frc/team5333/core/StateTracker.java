package frc.team5333.core;

import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team5333.core.monitor.PDPMonitor;
import frc.team5333.lib.FRCHooks;
import frc.team5333.lib.RobotData;
import frc.team5333.lib.RobotState;
import frc.team5333.lib.StateListener;

import java.util.ArrayList;
import java.util.List;

import static frc.team5333.lib.RobotState.*;

/**
 * The main class responsible for iterating the robot through its functions and
 * detecting State changes.
 *
 * Most of this is from the {@link edu.wpi.first.wpilibj.IterativeRobot} class, but
 * with a few changes (EventSystem and State Tracking)
 *
 * @author Jaci
 */
public class StateTracker {

    static boolean _state_disabled_init = false;
    static boolean _state_autonomous_init = false;
    static boolean _state_teleop_init = false;
    static boolean _state_test_init = false;

    public static RobotState currentState;
    public static RobotState lastState;

    private static RobotImpl impl;

    private static List<StateListener> listeners = new ArrayList<StateListener>();

    /**
     * Start the StateTracker loop
     */
    public static void init(RobotImpl impl) {
        UsageReporting.report(FRCNetworkCommunicationsLibrary.tResourceType.kResourceType_Framework, FRCNetworkCommunicationsLibrary.tInstances.kFramework_Iterative);
        RobotData.blackboard.put("alive", true);
        StateTracker.impl = impl;
        boolean isAlive;
        while ((isAlive = (Boolean)RobotData.blackboard.get("alive"))) {
            if (impl.isDisabled()) {
                if (!_state_disabled_init) {
                    transition(DISABLED);

                    _state_disabled_init = true;
                    _state_autonomous_init = false;
                    _state_teleop_init = false;
                    _state_test_init = false;
                }

                if (nextPeriodReady()) {
                    FRCHooks.observeDisabled();
                    tick(RobotState.DISABLED);
                }
            } else if (impl.isAutonomous()) {
                if (!_state_autonomous_init) {
                    transition(AUTONOMOUS);

                    _state_autonomous_init = true;
                    _state_disabled_init = false;
                    _state_teleop_init = false;
                    _state_test_init = false;
                }

                if (nextPeriodReady()) {
                    FRCHooks.observeAutonomous();
                    tick(RobotState.AUTONOMOUS);
                }
            } else if (impl.isTest()) {
                if (!_state_test_init) {
                    transition(TEST);

                    _state_test_init = true;
                    _state_disabled_init = false;
                    _state_autonomous_init = false;
                    _state_teleop_init = false;
                }

                if (nextPeriodReady()) {
                    FRCHooks.observeTest();
                    tick(RobotState.TEST);
                }
            } else {                //Teleop
                if (!_state_teleop_init) {
                    transition(TELEOP);

                    _state_teleop_init = true;
                    _state_disabled_init = false;
                    _state_autonomous_init = false;
                    _state_test_init = false;
                }

                if (nextPeriodReady()) {
                    FRCHooks.observeTeleop();
                    tick(RobotState.TELEOP);
                }
            }

            PDPMonitor.tick();

            impl.station().waitForData();
        }
    }

    /**
     * Transition between the old state and the new (given) state
     */
    static void transition(RobotState state) {
        if (state == RobotState.TEST)                   //Set the LiveWindow state
            LiveWindow.setEnabled(true);
        else
            LiveWindow.setEnabled(false);

        lastState = currentState;
        currentState = state;
        RobotData.blackboard.put("state", currentState);
        RobotData.blackboard.put("prevState", lastState);

        for (StateListener ticker : listeners)
            ticker.transitionState(currentState, lastState);
    }

    public static void tick(RobotState state) {
        for (StateListener ticker : listeners)
            ticker.tickState(state);
    }

    public static void addTicker(StateListener ticker) {
        listeners.add(ticker);
    }

    private static boolean nextPeriodReady() {
        return impl.station().isNewControlData();
    }

}