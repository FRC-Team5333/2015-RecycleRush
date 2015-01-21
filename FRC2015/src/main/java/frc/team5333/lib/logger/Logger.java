package frc.team5333.lib.logger;


import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A logger defaulting to System.out and System.err for logging
 * the robot's actions
 *
 * @author Jaci
 */
public class Logger {

    public static final int ATTR_TIME = 1;
    public static final int ATTR_THREAD = 2;

    public static final int ATTR_DEFAULT = ATTR_TIME | ATTR_THREAD;

    public static final LogLevel INFO = new LogLevel("INFO");
    public static final LogLevel WARN = new LogLevel("WARN").setPrintStream(DriverStationPS.get());
    public static final LogLevel ERROR = new LogLevel("ERROR").setPrintStream(DriverStationPS.get());
    public static final LogLevel SEVERE = new LogLevel("SEVERE").setPrintStream(DriverStationPS.get());

    public DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy-hh:mm:ss");

    int attr;
    String name;

    public Logger(String name, int attributes) {
        this.attr = attributes;
        this.name = name;
    }

    String getTime() {
        return dateFormat.format(new Date());
    }

    /* LOGGING BEGIN */
    private void log(String message, String level, PrintStream ps) {
        StringBuilder builder = new StringBuilder();

        if ((attr & ATTR_TIME) == ATTR_TIME)
            builder.append("[" + getTime() + "] ");

        builder.append("[" + name + "] ");

        if ((attr & ATTR_THREAD) == ATTR_THREAD)
            builder.append("[" + Thread.currentThread().getName() + "] ");

        builder.append("[" + level + "] ");
        builder.append(message);

        ps.println(builder.toString());
    }

    public void log(String message, LogLevel level) {
        log(message, level.getName().toUpperCase(), level.getPrintSteam());
    }

    public void info(String message) {
        log(message, INFO);
    }

    public void warn(String message) {
        log(message, WARN);
    }

    public void error(String message) {
        log(message, ERROR);
    }

    public void severe(String message) {
        log(message, SEVERE);
    }


}
