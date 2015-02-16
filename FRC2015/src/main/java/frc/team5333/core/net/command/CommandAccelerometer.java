package frc.team5333.core.net.command;

import edu.wpi.first.wpilibj.hal.AccelerometerJNI;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.net.EnumDispatchers;
import frc.team5333.core.net.NetworkDispatcher;
import frc.team5333.core.net.NetworkedClient;

import static frc.team5333.core.RobotImpl.log;

public class CommandAccelerometer implements ICommand {

    @Override
    public String getCommandPrefix() {
        return "accel";
    }

    @Override
    public void runCommand(String[] args, NetworkedClient client) {
        log().info("Accelerometer: [X: " + AccelerometerJNI.getAccelerometerX() + " Y: " + AccelerometerJNI.getAccelerometerY() + " Z: " + AccelerometerJNI.getAccelerometerZ() + "]");
    }
}
