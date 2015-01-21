package frc.team5333.core.monitor;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import static frc.team5333.core.RobotImpl.*;

public class PDPMonitor {

    static PowerDistributionPanel panel;

    public static void init() {
        panel = new PowerDistributionPanel();
    }

    public static void parse(String[] cmd) {
        if (cmd[1].equalsIgnoreCase("voltage"))
            log().info("PDP Voltage: " + round(panel.getVoltage()));
        else if (cmd[1].equalsIgnoreCase("temp"))
            log().info("PDP Temperature: " + round(panel.getTemperature()));
        else if (cmd[1].equalsIgnoreCase("current"))
            if (cmd.length > 2)
                log().info("PDP Port Current: " + round(panel.getCurrent(Integer.parseInt(cmd[2]))));
            else
                log().info("PDP Total Current: " + round(panel.getTotalCurrent()));
        else if (cmd[1].equalsIgnoreCase("power"))
            log().info("PDP Total Power: " + round(panel.getTotalPower()));
        else if (cmd[1].equalsIgnoreCase("energy"))
            log().info("PDP Total Energy: " + round(panel.getTotalEnergy()));
    }

    public static double round(double d) {
        return Math.floor(d * 100) / 100;
    }

}
