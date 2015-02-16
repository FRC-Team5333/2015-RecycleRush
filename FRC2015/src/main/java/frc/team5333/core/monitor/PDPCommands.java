package frc.team5333.core.monitor;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.team5333.core.net.NetworkedClient;
import frc.team5333.core.net.command.ICommand;
import jaci.openrio.toast.core.monitoring.power.PDPMonitor;

import static jaci.openrio.toast.lib.math.MathHelper.*;
import static jaci.openrio.toast.core.monitoring.power.PDPMonitor.*;

import static frc.team5333.core.RobotImpl.log;

/**
 * Manages the monitoring of the Power Distribution Panel
 *
 * @author Jaci
 */
public class PDPCommands implements ICommand {

    @Override
    public String getCommandPrefix() {
        return "pdp";
    }

    public void runCommand(String[] cmd, NetworkedClient client) {
        if (cmd[0].equalsIgnoreCase("voltage"))
            log().info("Controller Voltage: " + round(ControllerPower.getInputVoltage(), 2));
        else if (cmd[0].equalsIgnoreCase("batvoltage"))
            log().info("Battery Voltage: " + round(panel().getVoltage(), 2));
        else if (cmd[0].equalsIgnoreCase("temp"))
            log().info("PDP Temperature: " + round(panel().getTemperature(), 2));
        else if (cmd[0].equalsIgnoreCase("current"))
            if (cmd.length > 1)
                log().info("PDP Port Current: " + round(panel().getCurrent(Integer.parseInt(cmd[1])), 2));
            else
                log().info("PDP Total Current: " + round(panel().getTotalCurrent(), 2));
        else if (cmd[0].equalsIgnoreCase("power"))
            log().info("PDP Total Power: " + round(panel().getTotalPower(), 2));
        else if (cmd[0].equalsIgnoreCase("energy"))
            log().info("PDP Total Energy: " + round(panel().getTotalEnergy(), 2));
        else if (cmd[0].equalsIgnoreCase("reset"))
            panel().clearStickyFaults();
    }

}
