package frc.team5333.core.net.command;

import frc.team5333.core.drive.AutonomousDriver;
import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.core.net.NetworkedClient;
import groovy.lang.GroovyObject;
import jaci.openrio.toast.core.loader.groovy.GroovyLoader;

import static frc.team5333.core.RobotImpl.log;

public class CommandAutoSet implements ICommand {

    @Override
    public String getCommandPrefix() {
        return "set";
    }

    @Override
    public void runCommand(String[] args, NetworkedClient client) {
        AutonomousDriver.id = args[0];
    }

}
