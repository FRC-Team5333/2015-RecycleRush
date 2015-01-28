package frc.team5333.core.monitor;

import com.sun.management.OperatingSystemMXBean;
import frc.team5333.core.net.command.ICommand;

import java.lang.management.ManagementFactory;

import static frc.team5333.core.RobotImpl.*;

/**
 * Monitors data about the RoboRIO itself
 *
 * @author Jaci
 */
public class RIOMonitor implements ICommand {

    @Override
    public String getCommandPrefix() {
        return "rio";
    }

    @Override
    public void runCommand(String[] args) {
        switch (args[0]) {
            default:
                throw new RuntimeException("Blah Blah Blah");
        }
    }
}