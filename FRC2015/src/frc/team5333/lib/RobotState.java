package frc.team5333.lib;

/**
 * An enumeration of all the valid Robot States
 *
 * @author Jaci
 */
public enum RobotState {
    DISABLED("disabled"),
    TELEOP("teleop"),
    AUTONOMOUS("autonomous"),
    TEST("test");

    public String state;

    RobotState(String state) {
        this.state = state;
    }
}
