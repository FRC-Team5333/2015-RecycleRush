package frc.team5333.driver.control.drive;

/**
 * The ThrottleScale is a Virtual-Gearbox of sorts. It allows us to scale
 * the input coming in from the controller and before it is sent to the robot.
 * This allows us to adjust sensitivity and make sure we don't crash into something
 * we're not meant to
 *
 * @author Jaci
 */
public class ThrottleScale {

    private static int scale = 50;

    public static void setScale(int s) {
        scale = s;
    }

    public static double getScale() {
        return scale / 100D;
    }

    public static double scale(double t) {
        return t * getScale();
    }

    public static void increment() {
        scale = cut(scale + 10);
        update();
    }

    public static void decrement() {
        scale = cut(scale - 10);
        update();
    }

    private static int cut(int in) {
        return (int) Math.max(Math.min(in, 100D), -100D);
    }
    
    public static void update() {
    	DriveController.updateLeft();
    	DriveController.updateRight();
        DriveController.updateClamp();
        DriveController.updateLift();
    }

}
