package frc.team5333.core.io;

import frc.team5333.core.drive.RobotDriveTracker;
import frc.team5333.lib.Ports;

public class LimitCallback implements IOCallback {

    int type;

    public LimitCallback(int type) {
        this.type = type;
    }

    boolean last = false;

    @Override
    public void tick(Ports port) {
        boolean value = port.getDI().get();
        if (value != last) {
            if (type == 0)
                RobotDriveTracker.lift_max = value ? 0D : 1D;
            else
                RobotDriveTracker.lift_min = value ? 0D : 1D;
            last = value;
        }
    }

}
