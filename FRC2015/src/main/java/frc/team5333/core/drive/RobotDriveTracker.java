package frc.team5333.core.drive;

import edu.wpi.first.wpilibj.*;
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

    public static boolean lift_drop;
    public static boolean lift_rise;

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
        DigitalInput lift_top = Ports.LIFT_MAX_LIMIT.getDI();
        DigitalInput lift_bottom = Ports.LIFT_MIN_LIMIT.getDI();

        DigitalInput clamp0 = Ports.CLAMP_0.getDI();
        DigitalInput clamp1 = Ports.CLAMP_1.getDI();

        boolean clampE = !(clamp0.get() && clamp1.get());
        //boolean clampE = true;

        lift_rise = !lift_top.get();
        lift_drop = !lift_bottom.get();

        drive.tankDrive(left, right, true);

        otherDrive.tankDrive(clampE ? clamp : Math.max(clamp, 0), limit_lift(), true);
    }

    static double limit_lift() {
        double val = lift;
        if (lift_drop)
            val = Math.min(val, 0);
        if (lift_rise)
            val = Math.max(val, 0);
        return -val;
    }

    public static SpeedController[] getControllers() {
        SpeedController[] map = new SpeedController[4];
        map[0] = leftMotor;
        map[1] = rightMotor;
        map[2] = clampMotor;
        map[3] = liftMotor;
        return map;
    }

    @Override
    public void tickState(RobotState state) {
        if (state != RobotState.DISABLED)
            update();
    }
}
