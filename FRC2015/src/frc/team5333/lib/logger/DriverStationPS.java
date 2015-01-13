package frc.team5333.lib.logger;

import edu.wpi.first.wpilibj.DriverStation;
import frc.team5333.core.RobotImpl;

import java.io.*;

/**
 * A class for pairing the Log to the DriverStation error
 *
 * @author Jaci
 */
public class DriverStationPS extends PrintStream {

    static DriverStationPS instance;
    public static DriverStationPS get() {
        if (instance == null) instance = new DriverStationPS();
        return instance;
    }

    public DriverStationPS() {
        super(new OutputStream() {
            public StringBuilder builder = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                builder.append((char) b);
            }

            @Override
            public void flush() {
                DriverStation.reportError(builder.toString(), false);
                builder = new StringBuilder();
            }
        });
    }

}
