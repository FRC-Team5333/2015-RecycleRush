package frc.team5333.core;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team5333.core.drive.AutonomousDriver;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.io.IOManager;
import frc.team5333.core.io.UserButton;
import frc.team5333.core.net.EnumDispatchers;
import frc.team5333.core.net.command.*;
import frc.team5333.lib.RobotData;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.core.Toast;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.ToastModule;

/**
 * The base robot class designed to hook into the rest of the program
 *
 * @author Jaci
 */
public class RobotImpl extends ToastModule {

    static Logger logger;

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
        return Toast.getToast().station();
    }

    /**
     * Get the robot instance
     */
    public static RobotImpl getInstance() {
        return (RobotImpl) RobotData.blackboard.get("RobotImpl");
    }

    @Override
    public String getModuleName() {
        return "Team 5333";
    }

    @Override
    public String getModuleVersion() {
        return "1.0.0";
    }

    /**
     * Called upon Manifest load, when the jar is loaded into the classpath.
     * Use this for Pre-Initialization tasks
     */
    @Override
    public void prestart() {
        log().info("Prestart Phase Begun...");

        StateTracker.addTicker(new UserButton());
        IOManager.init();

        RobotData.blackboard.put("Team", 5333);
        RobotData.blackboard.put("RobotImpl", this);
        EnumDispatchers.start();

        RobotDriveTracker.prestart();
        AutonomousDriver.init();
        Logger.addHandler(new LogManager());

        log().info("Prestart Phase End...");
    }

    /**
     * Called when the robot is ready to operate and the WPILib is set up
     * Use this for Initialization tasks
     */
    @Override
    public void start() {
        log().info("Main Program Starting...");

        CommandRegistry.init();
        CommandRegistry.register(new CommandMotor());
        CommandRegistry.register(new CommandAccelerometer());
        CommandRegistry.register(new CommandDIO());
        CommandRegistry.register(new CommandAutoRecord());
        CommandRegistry.register(new CommandAutoPlayback());
        CommandRegistry.register(new CommandAutoSet());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log().info("Shutting down robot...");
                EnumDispatchers.stop();
            }
        });
    }

}
