package frc.team5333.core.net.command;

import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.net.NetworkedClient;

public class CommandMotor implements ICommand {

    @Override
    public String getCommandPrefix() {
        return "motor";
    }

    @Override
    public void runCommand(String[] args, NetworkedClient client) {
        double val;
        switch(args[0]) {
            case "l":
                val = Double.parseDouble(args[1]);
                RobotDriveTracker.setLeft(val);
                break;
            case "r":
                val = Double.parseDouble(args[1]);
                RobotDriveTracker.setRight(val);
                break;
        }

    }
}
