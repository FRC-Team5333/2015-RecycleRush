package frc.team5333.core;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.net.NetworkDispatcher;
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

        RobotData.blackboard.put("Team", 5333);
        RobotData.blackboard.put("RobotImpl", this);
        NetworkDispatcher dispatcher = new NetworkDispatcher();
        RobotData.blackboard.putIfAbsent("network:control:dispatch", dispatcher);
        dispatcher.start();

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

        LiveWindow.setEnabled(false);
        profiler().endSection("start");

        StateTracker.init(this);
        log().info("No longer alive, program exiting...");
        shutdown();

    }

    /**
     * The common method that will shutdown the robot safely and run any
     * tasks required for shutdown
     */
    public static void shutdown() {
        log().info("Robot Shutting Down");
        System.exit(0);
    }

}
