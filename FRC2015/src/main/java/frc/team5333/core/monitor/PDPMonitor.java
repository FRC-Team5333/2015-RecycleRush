package frc.team5333.core.monitor;

import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.team5333.core.net.command.ICommand;
import frc.team5333.lib.EvictingQueue;

import static frc.team5333.core.RobotImpl.log;

/**
 * Manages the monitoring of the Power Distribution Panel
 *
 * @author Jaci
 */
public class PDPMonitor implements ICommand {

    public static EvictingQueue<PDPPoll> pollHistory = new EvictingQueue<PDPPoll>(10);

    static PowerDistributionPanel panel;

    public static void init() {
        panel = new PowerDistributionPanel();
    }

    @Override
    public String getCommandPrefix() {
        return "pdp";
    }

    public void runCommand(String[] cmd) {
        if (cmd[0].equalsIgnoreCase("voltage"))
            log().info("Controller Voltage: " + round(ControllerPower.getInputVoltage()));
        else if (cmd[0].equalsIgnoreCase("batvoltage"))
            log().info("Battery Voltage: " + round(panel.getVoltage()));
        else if (cmd[0].equalsIgnoreCase("temp"))
            log().info("PDP Temperature: " + round(panel.getTemperature()));
        else if (cmd[0].equalsIgnoreCase("current"))
            if (cmd.length > 1)
                log().info("PDP Port Current: " + round(panel.getCurrent(Integer.parseInt(cmd[1]))));
            else
                log().info("PDP Total Current: " + round(panel.getTotalCurrent()));
        else if (cmd[0].equalsIgnoreCase("power"))
            log().info("PDP Total Power: " + round(panel.getTotalPower()));
        else if (cmd[0].equalsIgnoreCase("energy"))
            log().info("PDP Total Energy: " + round(panel.getTotalEnergy()));
        else if (cmd[0].equalsIgnoreCase("reset"))
            panel.clearStickyFaults();
    }

    public static double round(double d) {
        return Math.floor(d * 100) / 100;
    }

    public static void tick() {
        double[] current = new double[16];
        for (int i = 0; i < 16; i++) {
            current[i] = panel.getCurrent(i);
        }
        PDPPoll poll = new PDPPoll(ControllerPower.getInputVoltage(), current);
        pollHistory.add(poll);

        if (!ControllerPower.getEnabled3V3() || !ControllerPower.getEnabled5V() || !ControllerPower.getEnabled6V()) {
            //We've browned-out
            PDPPoll highest = null;
            PDPPoll lowestVoltage = null;
            PDPPoll[] array =  pollHistory.toArray(new PDPPoll[0]);
            for (PDPPoll p : array) {
                if (p == null) continue;
                if (highest == null || p.totalCurrentDraw > highest.totalCurrentDraw)
                    highest = p;
                if (lowestVoltage == null || lowestVoltage.batteryVoltage > p.batteryVoltage)
                    lowestVoltage = p;
            }

            if (lowestVoltage != null && highest != null) {
                log().error("Robot has browned-out. Lowest Voltage: " + round(lowestVoltage.batteryVoltage));
                log().error("\tHighest Current Draw: " + highest.totalCurrentDraw);
                for (int i = 0; i < highest.portCurrent.length; i++) {
                    log().error(String.format("\tPort %s current draw: %s", i, highest.portCurrent[i]) + (highest.highestIndex == i ? " (HIGHEST) " : ""));
                }
            }
        }
    }

}
