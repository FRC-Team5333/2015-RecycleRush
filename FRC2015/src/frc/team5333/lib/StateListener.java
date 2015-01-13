package frc.team5333.lib;

/**
 * Ticks objects implementing this interface when the robot does something
 * related to state transitions or loops
 *
 * @author Jaci
 */
public interface StateListener {

    public void tickState(RobotState state);

    public void transitionState(RobotState state, RobotState oldState);

}
