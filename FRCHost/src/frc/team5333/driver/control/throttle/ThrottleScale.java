package frc.team5333.driver.control.throttle;

public class ThrottleScale {

    private static int scale = 50;

    public static double getScale() {
        return scale / 100D;
    }

    public static double scale(double t) {
        return t * getScale();
    }

    public static void increment() {
        scale = cut(scale + 10);
    }

    public static void decrement() {
        scale = cut(scale - 10);
    }

    private static int cut(int in) {
        return (int) Math.max(Math.min(in, 100D), -100D);
    }

}
