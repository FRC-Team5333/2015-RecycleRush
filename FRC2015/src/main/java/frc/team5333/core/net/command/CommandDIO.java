package frc.team5333.core.net.command;

import edu.wpi.first.wpilibj.hal.AccelerometerJNI;
import frc.team5333.core.net.NetworkedClient;
import frc.team5333.lib.Ports;

import static frc.team5333.core.RobotImpl.log;

public class CommandDIO implements ICommand {

    @Override
    public String getCommandPrefix() {
        return "dio";
    }

    @Override
    public void runCommand(String[] args, NetworkedClient client) {
        switch (args[0]) {
            case "LIFTMAX":
                log().info("Lift Max " + Ports.LIFT_MAX_LIMIT.getDI().get());
                break;
            case "LIFTMIN":
                log().info("Lift Min " + Ports.LIFT_MIN_LIMIT.getDI().get());
                break;
            case "CLAMP0":
                log().info("Clamp 0 " + Ports.CLAMP_0.getDI().get());
                break;
            case "CLAMP1":
                log().info("Clamp 1 " + Ports.CLAMP_1.getDI().get());
                break;
        }
    }
}
