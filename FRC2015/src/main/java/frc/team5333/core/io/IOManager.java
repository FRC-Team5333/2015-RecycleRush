package frc.team5333.core.io;

import frc.team5333.lib.Ports;

/**
 * Manage IO that isn't covered by other sections of code
 *
 * @author Jaci
 */
public class IOManager {

    public static void setDigital(Ports port, boolean state) {
        port.getDO().set(state);
    }

}
