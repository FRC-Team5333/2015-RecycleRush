package frc.team5333.core.drive;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import frc.team5333.lib.Ports;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.lib.state.RobotState;
import jaci.openrio.toast.lib.state.StateListener;

/**
 * Responsible for driving the robot's motors
 *
 * @author Jaci
 */
public class RobotDriveTracker implements StateListener.Ticker {

    static double left;
    static double right;
    static double clamp;
    static double lift;

    public static double lift_max;
    public static double lift_min;

    static RobotDrive drive;
    static Talon leftMotor;
    static Talon rightMotor;
    static RobotDrive otherDrive;
    static Victor clampMotor;
    static Victor liftMotor;

    public static void prestart() {
        leftMotor = new Talon(Ports.LEFT_DRIVE.getPort());
        rightMotor = new Talon(Ports.RIGHT_DRIVE.getPort());

        clampMotor = new Victor(Ports.CLAMP.getPort());
        liftMotor = new Victor(Ports.LIFT.getPort());

        drive = new RobotDrive(leftMotor, rightMotor);
        otherDrive = new RobotDrive(clampMotor, liftMotor);
        StateTracker.addTicker(new RobotDriveTracker());
    }

    public static void setLeft(double d) {
        left = d;
    }

    public static void setRight(double d) {
        right = d;
    }

    public static void setClamp(double d) {
        clamp = d;
    }

    public static void setLift(double d) {
        lift = d;
    }

    public static void setBoth(double d) {
        setLeft(d);
        setRight(d);
    }

    public static void setAll(double d) {
        setBoth(d);
        setClamp(d);
        setLift(d);
    }

    public static double getLeft() {
        return left;
    }

    public static double getRight() {
        return right;
    }

    public static double getClamp() {
        return clamp;
    }

    public static double getLift() {
        return lift;
    }

    public static void update() {
        drive.tankDrive(left, right, true);

        otherDrive.tankDrive(clamp, lift, true);
    }

    static double limit_lift() {
        return Math.max(Math.min(lift_max, lift), lift_min);
    }

    @Override
    public void tickState(RobotState state) {
        if (state != RobotState.DISABLED)
            update();
    }
}
