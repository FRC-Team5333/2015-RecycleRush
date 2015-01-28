package frc.team5333.core;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.io.IOManager;
import frc.team5333.core.io.UserButton;
import frc.team5333.core.monitor.PDPMonitor;
import frc.team5333.core.monitor.RIOMonitor;
import frc.team5333.core.net.EnumDispatchers;
import frc.team5333.core.net.command.CommandRegistry;
import frc.team5333.lib.FRCHooks;
import frc.team5333.lib.RobotData;
import frc.team5333.lib.logger.Logger;
import frc.team5333.lib.profiler.Profiler;

/**
 * The base robot class designed to hook into the rest of the program
 *
 * @author Jaci
 */
public class RobotImpl extends RobotBase {

    static Profiler profiler;
    static Logger logger;

    /**
     * Get the Profiler for the robot. This is used for tracking the impact
     * or load of certain sections of code on the robot
     */
    public static Profiler profiler() {
        if (profiler == null) profiler = new Profiler("RobotImpl");
        return profiler;
    }

    /**
     * Get the logger for the robot. This is used to log data to the robot's console
     * and the driver station if passed the correct logLevel
     */
    public static Logger log() {
        if (logger == null) logger = new Logger("RobotImpl", Logger.ATTR_DEFAULT);
        return logger;
    }

    /**
     * Get the DriverStation object
     */
    public DriverStation station() {
        return m_ds;
    }

    /**
     * Get the robot instance
     */
    public static RobotImpl getInstance() {
        return (RobotImpl) RobotData.blackboard.get("RobotImpl");
    }

    /**
     * Called upon Manifest load, when the jar is loaded into the classpath.
     * Use this for Pre-Initialization tasks
     */
    @Override
    protected void prestart() {
        log().info("Prestart Phase Begun...");
        profiler().beginSection("prestart");

        StateTracker.addTicker(new UserButton());
        StateTracker.addTicker(new IOManager());

        RobotData.blackboard.put("Team", 5333);
        RobotData.blackboard.put("RobotImpl", this);
        EnumDispatchers.start();

        RobotDriveTracker.prestart();

        FRCHooks.robotReady();

        profiler().endSection("prestart");
        log().info("Prestart Phase End...");
    }

    /**
     * Called when the robot is ready to operate and the WPILib is set up
     * Use this for Initialization tasks
     */
    @Override
    public void startCompetition() {
        log().info("Main Program Starting...");
        profiler().beginSection("start");

        PDPMonitor.init();
        CommandRegistry.init();

        LiveWindow.setEnabled(false);
        profiler().endSection("start");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log().info("Shutting down robot...");
                EnumDispatchers.stop();
            }
        });

        StateTracker.init(this);
        log().info("No longer alive, program exiting...");
    }

}
